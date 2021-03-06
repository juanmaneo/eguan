package io.eguan.nbdsrv;

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

import io.eguan.srv.ServerPortConfigKey;

/**
 * Key defining the port of the NBD server.
 * 
 * <table border='1'>
 * <tr>
 * <th>NAME</th>
 * <th>DESCRIPTION</th>
 * <th>REQUIRED</th>
 * <th>UNIT</th>
 * <th>TYPE</th>
 * <th>DEFAULT</th>
 * <th>MIN</th>
 * <th>MAX</th>
 * </tr>
 * <tr>
 * <td>{@value ServerPortConfigKey#NAME}</td>
 * <td>Binding port of the NBD server.</td>
 * <td>FALSE</td>
 * <td></td>
 * <td>int</td>
 * <td>{@value #DEFAULT_VALUE}</td>
 * <td>1</td>
 * <td>{@value ServerPortConfigKey#MAX_VALUE}</td>
 * </tr>
 * </table>
 * 
 * @author oodrive
 * @author llambert
 * 
 */
public final class NbdServerPortConfigKey extends ServerPortConfigKey {

    private static final int DEFAULT_VALUE = NbdServer.DEFAULT_NBD_PORT;
    private static final Integer DEFAULT_VALUE_OBJ = Integer.valueOf(DEFAULT_VALUE);

    private static final NbdServerPortConfigKey INSTANCE = new NbdServerPortConfigKey();

    public static NbdServerPortConfigKey getInstance() {
        return INSTANCE;
    }

    private NbdServerPortConfigKey() {
        super();
    }

    @Override
    protected final Integer getDefaultValue() {
        return DEFAULT_VALUE_OBJ;
    }

}
