package com.oodrive.nuage.vvr.configuration.keys;

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

import java.io.File;

import com.oodrive.nuage.configuration.FileConfigKey;
import com.oodrive.nuage.nrs.NrsStorageConfigKey;
import com.oodrive.nuage.vvr.persistence.repository.NrsDevice;

/**
 * Key holding the relative path based on {@link NrsStorageConfigKey} to store persistent data files associated to
 * {@link NrsDevice}s.
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
 * <td>Relative path based on {@link NrsStorageConfigKey} to store persistent data files associated to {@link NrsDevice}
 * s.</td>
 * <td>FALSE</td>
 * <td>relative directory path</td>
 * <td>{@link String}</td>
 * <td>devices</td>
 * <td>N/A</td>
 * <td>N/A</td>
 * </tr>
 * </table>
 * 
 * @author oodrive
 * @author llambert
 * 
 */
public final class DeviceFileDirectoryConfigKey extends FileConfigKey {

    protected static final String NAME = "nrs.devices.directory";

    private static final File DEFAULT_VALUE = new File("devices");

    private static final DeviceFileDirectoryConfigKey INSTANCE = new DeviceFileDirectoryConfigKey();

    /**
     * Gets the predefined singleton instance.
     * 
     * @return the singleton instance constructed by {@link #DeviceDirectoryConfigKey()}
     */
    public static final DeviceFileDirectoryConfigKey getInstance() {
        return INSTANCE;
    }

    /**
     * Constructs the singleton instance as a {@link FileConfigKey} using the unique name {@value #NAME}.
     */
    private DeviceFileDirectoryConfigKey() {
        // creates without any additional checks as this is a relative path
        super(NAME, false, false, false);
    }

    @Override
    public final File getDefaultValue() {
        return DEFAULT_VALUE;
    }

}
