package io.eguan.configuration;

import io.eguan.configuration.AbstractConfigKey;
import io.eguan.configuration.StringConfigKey;

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

/**
 * Implementation of {@link TestAbstractConfigKeys} for testing {@link StringConfigKey}s.
 * 
 * @author oodrive
 * @author pwehrle
 * 
 */
public final class TestStringConfigKey extends TestAbstractConfigKeys {

    @Override
    protected final AbstractConfigKey getTestKey(final boolean required, final boolean hasDefault) {
        return new StringConfigKey("test.string.key") {

            @Override
            protected final String getDefaultValue() {
                return hasDefault ? "default string" : null;
            }

            @Override
            public final boolean isRequired() {
                return required;
            }
        };
    }
}
