package io.eguan.main;

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

import io.eguan.utils.RunCmdErrorException;
import io.eguan.utils.RunCmdUtils;
import io.eguan.utils.RunCmdUtils.RunningCmd;
import io.eguan.vold.model.VoldTestHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ObjectArrays;

public class TestEguan {

    /** Invalid usage */
    private static final int EXIT_USAGE = 1;

    /** Config failed */
    private static final int EXIT_CONFIG_VOLD_FAILED = 2;
    private static final int EXIT_CONFIG_REST_FAILED = 3;
    private static final int EXIT_CONFIG_CINDER_FAILED = 4;

    /** Init failed */
    private static final int EXIT_INIT_REST_FAILED = 6;

    private void launchEguan(final String[] javaArgs) throws IOException {
        final RunningCmd eguan = createEguanCmd(javaArgs);
        eguan.run();
    }

    private RunningCmd createEguanCmd(final String[] javaArgs) {
        final String classpath = System.getProperty("java.class.path");
        final String main = Eguan.class.getName();
        String[] args = new String[] { "java", "-cp", classpath, main };
        if (javaArgs != null) {
            args = ObjectArrays.concat(args, javaArgs, String.class);
        }
        return RunCmdUtils.newRunningCmd(args, this);
    }

    @Test
    public void testNoArg() throws IOException {
        try {
            launchEguan(null);
            throw new AssertionError("Not reached");
        }
        catch (final RunCmdErrorException e) {
            Assert.assertEquals(EXIT_USAGE, e.getExitValue());
        }
    }

    @Test
    public void testVoldDirNotExist() throws IOException {

        final File tempDir = Files.createTempDirectory("vold-launch").toFile();
        tempDir.delete();
        try {
            final String configRest = TestEguan.class.getClassLoader().getResource("rest.cfg").getPath();
            final String[] args = { tempDir.getAbsolutePath(), configRest };
            launchEguan(args);
            throw new AssertionError("Not reached");
        }
        catch (final RunCmdErrorException e) {
            Assert.assertEquals(EXIT_CONFIG_VOLD_FAILED, e.getExitValue());
        }
    }

    @Test
    public void testRestConfigNotExist() throws IOException {

        final VoldTestHelper helper = new VoldTestHelper();
        helper.createTemporary();
        try {
            final File tempDir = Files.createTempDirectory("vold-launch").toFile();
            tempDir.delete();
            try {

                final String[] args = { helper.getVoldPath(), tempDir.getAbsolutePath() };
                launchEguan(args);
                throw new AssertionError("Not reached");
            }
            catch (final RunCmdErrorException e) {
                Assert.assertEquals(EXIT_CONFIG_REST_FAILED, e.getExitValue());
            }
        }
        finally {
            helper.destroy();
        }
    }

    @Test
    public void testCinderNotExecutable() throws IOException {
        final VoldTestHelper helper = new VoldTestHelper();
        helper.createTemporary();
        try {
            final Path tempDirPath = Files.createTempDirectory("cinder-launch");
            try {
                final Path tempFilePath = Files.createTempFile(tempDirPath, null, null,
                        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("---------")));
                final String configRest = TestEguan.class.getClassLoader().getResource("rest.cfg").getPath();
                final String[] args = { helper.getVoldPath(), configRest, tempFilePath.toFile().getAbsolutePath() };

                launchEguan(args);
                throw new AssertionError("Not reached");
            }
            catch (final RunCmdErrorException e) {
                Assert.assertEquals(EXIT_CONFIG_CINDER_FAILED, e.getExitValue());
            }
            finally {
                io.eguan.utils.Files.deleteRecursive(tempDirPath);
            }
        }
        finally {
            helper.destroy();
        }
    }

    @Test
    public void testBadRestConfig() throws IOException {
        final VoldTestHelper helper = new VoldTestHelper();
        helper.createTemporary();
        try {
            final String configRest = TestEguan.class.getClassLoader().getResource("badrest.cfg").getPath();
            final String[] args = { helper.getVoldPath(), configRest };

            launchEguan(args);
            throw new AssertionError("Not reached");
        }
        catch (final RunCmdErrorException e) {
            Assert.assertEquals(EXIT_INIT_REST_FAILED, e.getExitValue());
        }
        finally {
            helper.destroy();
        }
    }

    @Test(expected = RunCmdErrorException.class)
    public void testEguan5s() throws IOException {
        final VoldTestHelper helper = new VoldTestHelper();
        helper.createTemporary();
        try {
            final String configRest = TestEguan.class.getClassLoader().getResource("rest.cfg").getPath();
            final String[] args = { helper.getVoldPath(), configRest };

            final RunningCmd eguan = createEguanCmd(args);

            final Thread killer = new Thread(new Runnable() {
                @Override
                public final void run() {
                    try {
                        Thread.sleep(5000);
                    }
                    catch (final InterruptedException e) {
                        // Ignore
                    }
                    eguan.kill();
                }
            });
            killer.start();

            eguan.run();
        }
        finally {
            helper.destroy();
        }
    }

    @Test(expected = RunCmdErrorException.class)
    public void testEguan1s() throws IOException {
        final VoldTestHelper helper = new VoldTestHelper();
        helper.createTemporary();
        try {
            final String configRest = TestEguan.class.getClassLoader().getResource("rest.cfg").getPath();
            final String[] args = { helper.getVoldPath(), configRest };

            final RunningCmd eguan = createEguanCmd(args);

            final Thread killer = new Thread(new Runnable() {
                @Override
                public final void run() {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (final InterruptedException e) {
                        // Ignore
                    }
                    eguan.kill();
                }
            });
            killer.start();
            eguan.run();
        }
        finally {
            helper.destroy();
        }
    }

    @Test
    public void testEguanCinder() throws IOException {

        final VoldTestHelper helper = new VoldTestHelper();
        helper.createTemporary();
        try {
            final String configRest = TestEguan.class.getClassLoader().getResource("rest.cfg").getPath();
            final String cinderScript = TestEguan.class.getClassLoader().getResource("sleepTest").getPath();
            // The cinderScript may not be executable
            {
                final File cinderScriptFile = new File(cinderScript);
                Assert.assertTrue(cinderScriptFile.exists());
                cinderScriptFile.setExecutable(true);
            }
            final String[] args = { helper.getVoldPath(), configRest, cinderScript };
            final RunningCmd eguan = createEguanCmd(args);
            eguan.run();
        }
        finally {
            helper.destroy();
        }
    }

}
