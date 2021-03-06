package io.eguan.nbdsrv.packet;

/*
 * #%L
 * Project eguan
 * %%
 * Copyright (C) 2012 - 2016 Oodrive
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

import io.eguan.utils.ByteBufferCache;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Caches {@link ByteBuffer}s for the NBD server.
 * 
 * @author oodrive
 * @author llambert
 * 
 */
public final class NbdByteBufferCache {

    // Allocate direct buffers for data only, needs big endian to read network integers
    private static final ByteBufferCache BYTE_BUFFER_CACHE = new ByteBufferCache(Utils.MAX_HEADER_SIZE,
            ByteOrder.BIG_ENDIAN);

    /**
     * No instance.
     */
    private NbdByteBufferCache() {
        throw new Error();
    }

    /**
     * Allocate a new buffer
     * 
     * @param capacity
     * @return the new allocated or reused buffer. May not be filled with 0.
     */
    public static final ByteBuffer allocate(final int capacity) {
        return BYTE_BUFFER_CACHE.allocate(capacity);
    }

    /**
     * Release a buffer and make it available for reuse.
     * 
     * @param buffer
     *            buffer to release
     */
    public static final void release(final ByteBuffer buffer) {
        BYTE_BUFFER_CACHE.release(buffer);
    }
}
