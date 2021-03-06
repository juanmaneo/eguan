package io.eguan.vvr.configuration.keys;

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

import io.eguan.configuration.MetaConfiguration;
import io.eguan.configuration.UuidConfigKey;

import java.util.UUID;

/**
 * Key holding the uuid of the current Ibs, will be generated if required. If the Ibs is restarted it must be the same
 * as the one that has been previously used. Optional for a new Ibs deployment, mandatory in all other circumstances.
 * Mapped to {@value #IBS_CONFIG_KEY} in the IBS configuration file.
 * <p>
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
 * <td>Uuid of the current Ibs, will be generated if required. If the Ibs is restarted it must be the same as the one
 * that has been previously used. Optional for a new Ibs deployment, mandatory in all other circumstances. Mapped to
 * "uuid" in the IBS configuration file.</td>
 * <td>FALSE</td>
 * <td>RFC 4122 compliant string representation of a {@link UUID}</td>
 * <td>{@link String}</td>
 * <td>{@link UUID#randomUUID() random UUID}</td>
 * <td>N/A</td>
 * <td>N/A</td>
 * </tr>
 * </table>
 * 
 * @author oodrive
 * @author pwehrle
 * 
 */
public final class IbsUuidConfigKey extends UuidConfigKey implements IbsConfigKey {

    private static final String NAME = "uuid";

    private static final String IBS_CONFIG_KEY = "uuid";

    private static final UUID DEFAULT_VALUE = UUID.randomUUID();

    private static final IbsUuidConfigKey INSTANCE = new IbsUuidConfigKey();

    public static IbsUuidConfigKey getInstance() {
        return INSTANCE;
    }

    private IbsUuidConfigKey() {
        super(NAME);
    }

    @Override
    protected final UUID getDefaultValue() {
        return DEFAULT_VALUE;
    }

    @Override
    public final String getBackendConfigKey() {
        return IBS_CONFIG_KEY;
    }

    @Override
    public final String getBackendConfigValue(final MetaConfiguration configuration) {
        return valueToString(getTypedValue(configuration));
    }

}
