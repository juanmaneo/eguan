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

import io.eguan.configuration.BooleanConfigKey;
import io.eguan.configuration.MetaConfiguration;

/**
 * Key defining if hot data will be enabled, mapped to {@value #IBS_CONFIG_KEY} in the IBS configuration file.
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
 * <td>Defines if background compaction will be disabled, mapped to {@value #IBS_CONFIG_KEY} in the IBS configuration
 * file.</td>
 * <td>FALSE</td>
 * <td>either "true", "yes" or "false", "no" (case insensitive)</td>
 * <td>{@link String}</td>
 * <td>no</td>
 * <td>N/A</td>
 * <td>N/A</td>
 * </tr>
 * </table>
 * 
 * @author oodrive
 * @author jmcaba
 * @author ebredzinski
 * 
 */
public final class IbsDisableBackgroundCompactionForIbpgenConfigKey extends BooleanConfigKey implements IbsConfigKey {

    private static final String NAME = "disableBackgroundCompactionForIbpgen";

    private static final String IBS_CONFIG_KEY = "ldb_disable_background_compaction_for_ibpgen";

    private static final String IBS_VALUE_TRUE = "yes";

    private static final String IBS_VALUE_FALSE = "no";

    private static final IbsDisableBackgroundCompactionForIbpgenConfigKey INSTANCE = new IbsDisableBackgroundCompactionForIbpgenConfigKey();

    public static IbsDisableBackgroundCompactionForIbpgenConfigKey getInstance() {
        return INSTANCE;
    }

    private IbsDisableBackgroundCompactionForIbpgenConfigKey() {
        super(NAME);
    }

    @Override
    protected final Boolean getDefaultValue() {
        return Boolean.FALSE;
    }

    @Override
    public final String getBackendConfigKey() {
        return IBS_CONFIG_KEY;
    }

    @Override
    public final String getBackendConfigValue(final MetaConfiguration configuration) {
        if (getTypedValue(configuration)) {
            return IBS_VALUE_TRUE;
        }
        else {
            return IBS_VALUE_FALSE;
        }
    }

}
