package com.oodrive.nuage.nbdsrv.client;

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

import com.oodrive.nuage.nbdsrv.packet.DataPushingCmd;
import com.oodrive.nuage.nbdsrv.packet.DataPushingPacket;

/**
 * Represents the task to disconnect the client.
 * 
 * @author oodrive
 * @author ebredzinski
 * 
 */
class DisconnectTask extends TaskAbstract {

    /** NBD client */
    private final NbdClient nbdClient;

    DisconnectTask(final NbdClient nbdClient) {
        this.nbdClient = nbdClient;
    }

    @Override
    public final Boolean call() throws IOException {
        final DataPushingPacket dataPushingPacket = new DataPushingPacket(DataPushingPacket.MAGIC,
                DataPushingCmd.NBD_CMD_DISC, getHandle(), 0, 0);

        final ByteBuffer header = DataPushingPacket.serialize(dataPushingPacket);
        try {
            nbdClient.writeSocket(header);
            nbdClient.close();
        }
        finally {
            DataPushingPacket.release(header);
        }
        return Boolean.TRUE;
    }

}
