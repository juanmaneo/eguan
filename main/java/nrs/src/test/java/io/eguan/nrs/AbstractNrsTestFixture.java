package io.eguan.nrs;

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

import org.junit.After;
import org.junit.Before;
import org.junit.runners.model.InitializationError;

public abstract class AbstractNrsTestFixture {

    private final NrsInstallationHelper helper = new NrsInstallationHelper();

    /**
     * @return the configuration
     */
    protected final MetaConfiguration getConfiguration() {
        return helper.getConfiguration();
    }

    @Before
    public void setUpNrs() throws InitializationError {
        helper.setUpNrs();
    }

    @After
    public void tearDownNrs() throws InitializationError {
        helper.tearDownNrs();
    }

}
