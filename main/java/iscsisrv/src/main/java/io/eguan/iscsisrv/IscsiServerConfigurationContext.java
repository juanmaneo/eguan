package io.eguan.iscsisrv;

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

import io.eguan.configuration.AbstractConfigurationContext;

import javax.annotation.concurrent.Immutable;

/**
 * Configuration context for the iSCSI server.
 * 
 * @author oodrive
 * @author llambert
 * 
 */
@Immutable
public final class IscsiServerConfigurationContext extends AbstractConfigurationContext {

    protected static final String NAME = "io.eguan.iscsisrv";

    private static final IscsiServerConfigurationContext INSTANCE = new IscsiServerConfigurationContext();

    /**
     * Gets the singleton instance of this context.
     * 
     * @return the instance constructed by {@link #IscsiServerConfigurationContext()}
     */
    public static final IscsiServerConfigurationContext getInstance() {
        return INSTANCE;
    }

    /**
     * Constructs an instance with the given {@link #NAME} and all keys references by this context.
     */
    private IscsiServerConfigurationContext() {
        super(NAME, IscsiServerPortConfigKey.getInstance(), IscsiServerInetAddressConfigKey.getInstance());
    }

}
