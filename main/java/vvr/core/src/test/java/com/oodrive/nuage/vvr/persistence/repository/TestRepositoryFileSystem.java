package com.oodrive.nuage.vvr.persistence.repository;

/*
 * #%L
 * Project eguan
 * %%
 * Copyright (C) 2012 - 2015 Oodrive
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

import java.util.Collection;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.oodrive.nuage.nrs.TestValidNrsConfigurationMountedContext;
import com.oodrive.nuage.vvr.configuration.AbstractVvrCommonFixture;

/**
 * Try to create a repository on invalid file systems.
 * 
 * @author oodrive
 * @author llambert
 * 
 */
@RunWith(value = Parameterized.class)
public class TestRepositoryFileSystem extends AbstractVvrCommonFixture {

    @Parameters
    public static Collection<Object[]> getMountConfig() {
        return TestValidNrsConfigurationMountedContext.getMountConfig();
    }

    public TestRepositoryFileSystem(final String helpersNrsFsType, final String helpersNrsMntOptions) {
        super(helpersNrsFsType, helpersNrsMntOptions);
    }

    @Test(expected = IllegalStateException.class)
    public void createRepository() {
        // Try to create a repository
        final NrsRepository.Builder vvrBuilder = new NrsRepository.Builder();
        vvrBuilder.configuration(getConfiguration());
        vvrBuilder.uuid(UUID.randomUUID());
        vvrBuilder.ownerId(UUID.randomUUID());
        vvrBuilder.nodeId(UUID.randomUUID());
        vvrBuilder.rootUuid(UUID.randomUUID());
        vvrBuilder.create();
    }
}
