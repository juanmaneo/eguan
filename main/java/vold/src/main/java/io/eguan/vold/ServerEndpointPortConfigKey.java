package io.eguan.vold;

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

import io.eguan.configuration.IntegerConfigKey;

/**
 * Key defining the TCP port on which the VOLD synchronization server listens to incoming connections.
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
 * <td>{@value #NAME}</td>
 * <td>The TCP port on which the VOLD synchronization server listens to incoming connections</td>
 * <td>FALSE</td>
 * <td></td>
 * <td>int</td>
 * <td>{@value #DEFAULT_VALUE}</td>
 * <td>{@value #MIN_VALUE}</td>
 * <td>{@value #MAX_VALUE}</td>
 * </tr>
 * </table>
 * 
 * @author oodrive
 * @author llambert
 * 
 */
public final class ServerEndpointPortConfigKey extends IntegerConfigKey {

    protected static final String NAME = "server.port";

    public static final int MAX_VALUE = 65535;

    public static final int MIN_VALUE = 1025;

    private static final int DEFAULT_VALUE = 53670;

    private static final ServerEndpointPortConfigKey INSTANCE = new ServerEndpointPortConfigKey();

    /**
     * Gets the predefined singleton instance.
     * 
     * @return the singleton instance constructed by {@link #ServerPortConfigKey()}
     */
    public static final ServerEndpointPortConfigKey getInstance() {
        return INSTANCE;
    }

    private ServerEndpointPortConfigKey() {
        super(NAME, MIN_VALUE, MAX_VALUE);
    }

    @Override
    protected final Integer getDefaultValue() {
        return Integer.valueOf(DEFAULT_VALUE);
    }

}
