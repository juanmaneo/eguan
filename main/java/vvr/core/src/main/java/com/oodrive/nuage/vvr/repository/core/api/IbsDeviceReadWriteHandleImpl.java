package com.oodrive.nuage.vvr.repository.core.api;

/*
 * #%L
 * Project eguan
 * %%
 * Copyright (C) 2012 - 2014 Oodrive
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.protobuf.ByteString;
import com.oodrive.nuage.hash.HashAlgorithm;
import com.oodrive.nuage.ibs.Ibs;
import com.oodrive.nuage.ibs.IbsErrorCode;
import com.oodrive.nuage.ibs.IbsException;
import com.oodrive.nuage.ibs.IbsIOException;
import com.oodrive.nuage.proto.vvr.VvrRemote;
import com.oodrive.nuage.proto.vvr.VvrRemote.RemoteOperation.Builder;
import com.oodrive.nuage.utils.ByteArrays;
import com.oodrive.nuage.vvr.repository.core.api.Device.ReadWriteHandle;

/**
 * Implementation of a {@link ReadWriteHandle} writing directly blocks in the {@link Ibs}.
 * 
 * @author oodrive
 * @author llambert
 * 
 */
final class IbsDeviceReadWriteHandleImpl extends DeviceReadWriteHandleImpl {

    /** The {@link Ibs} on which to perform read/write operations. */
    private final Ibs ibs;
    /** <code>true</code> if the hot data is enabled in {@link Ibs} */
    private final boolean ibsHotDataEnabled;

    IbsDeviceReadWriteHandleImpl(final AbstractDeviceImplHelper deviceImplHelper, @Nonnull final Ibs ibs,
            final HashAlgorithm hashAlgorithm, final boolean readOnly, final int blockSize) {
        super(deviceImplHelper, hashAlgorithm, readOnly, blockSize);
        this.ibs = Objects.requireNonNull(ibs);
        this.ibsHotDataEnabled = ibs.isHotDataEnabled();
    }

    @Override
    final int createBlockTransaction() throws IbsException, IllegalArgumentException, IbsIOException {
        return ibs.createTransaction();
    }

    @Override
    final void commitBlockTransaction(final int txId) throws IbsException, IllegalArgumentException, IbsIOException {
        ibs.commit(txId);
    }

    @Override
    final void rollbackBlockTransaction(final int txId) throws IbsException, IllegalArgumentException, IbsIOException {
        ibs.rollback(txId);
    }

    @Override
    final boolean canReplaceOldKey() {
        return ibsHotDataEnabled;
    }

    @Override
    final boolean needsBlockOpBuilder() {
        return true;
    }

    @Override
    final void notifyBlockIO(@Nonnull final Builder blockOpBuilder) {
        if (blockOpBuilder.getIbsCount() > 0) {
            deviceImplHelper.notifyIO(blockOpBuilder);
        }
    }

    @Override
    final void storeNewBlock(final ByteBuffer block, final int offset, final long blockIndex, final byte[] newKey,
            final byte[] oldKey, final int ibsTxId, final VvrRemote.RemoteOperation.Builder opBuilder) throws IbsException,
            IllegalArgumentException, IndexOutOfBoundsException, NullPointerException, IOException {

        // Store in IBS: replace when possible
        final boolean newBlock;
        if (ibsTxId > 0) {
            if (oldKey == null) {
                newBlock = ibs.put(ibsTxId, newKey, block, offset, blockSize);
            }
            else {
                newBlock = ibs.replace(ibsTxId, oldKey, newKey, block, offset, blockSize);
            }
        }
        else {
            if (oldKey == null) {
                newBlock = ibs.put(newKey, block, offset, blockSize);
            }
            else {
                newBlock = ibs.replace(oldKey, newKey, block, offset, blockSize);
            }
        }

        // Fill IBS notification
        final VvrRemote.Ibs.Builder ibsBuilder = VvrRemote.Ibs.newBuilder();
        ibsBuilder.setKey(ByteString.copyFrom(newKey));
        if (newBlock) {
            // Need to set position and limit once again
            block.position(offset);
            block.limit(offset + blockSize);
            ibsBuilder.setValue(ByteString.copyFrom(block));
        }
        if (oldKey != null) {
            ibsBuilder.setKeyOld(ByteString.copyFrom(oldKey));
        }

        synchronized (opBuilder) {
            opBuilder.addIbs(ibsBuilder);
        }

        // Store key in persistence
        deviceImplHelper.writeBlockKey(blockIndex, newKey);
    }

    @Override
    final ByteBuffer getBlock(final byte[] key, final UUID srcNode, final boolean readOnly) throws IbsException,
            IbsIOException, InterruptedException {
        try {
            final ByteBuffer block = allocateBlock(false);
            ibs.get(key, block);
            return block;
        }
        catch (final IbsIOException e) {
            if (e.getErrorCode() == IbsErrorCode.NOT_FOUND) {
                // Look for the block on remote nodes
                final ByteString byteString = deviceImplHelper.getRemoteBuffer(key, srcNode);
                if (byteString != null) {
                    // Block found
                    final ByteBuffer result;
                    if (readOnly) {
                        result = byteString.asReadOnlyByteBuffer();
                        assert result.capacity() == blockSize;
                    }
                    else {
                        result = allocateBlock(false);
                        byteString.copyTo(result);
                    }
                    return result;
                }
            }
            throw e;
        }
    }

    @Override
    final void fillBlock(final byte[] key, final ByteBuffer data, final int dataOffset, final UUID srcNode)
            throws IOException, IbsException, IbsIOException, InterruptedException {
        int readLen = 0;
        try {
            readLen = ibs.get(key, data, dataOffset, blockSize);
        }
        catch (final IbsIOException e) {
            if (e.getErrorCode() == IbsErrorCode.NOT_FOUND) {
                // Look for the block on remote nodes
                final ByteString byteString = deviceImplHelper.getRemoteBuffer(key, srcNode);
                if (byteString != null) {
                    // Found: copy block to data
                    data.rewind().position(dataOffset);
                    byteString.copyTo(data);
                    readLen = data.position() - dataOffset;
                }
            }
            // Block found?
            if (readLen == 0) {
                throw e;
            }
        }
        if (readLen != blockSize) {
            throw new IOException("Failed to read key=0x" + ByteArrays.toHex(key) + ", blockSize=" + blockSize
                    + ", readlen=" + readLen);
        }
    }

}
