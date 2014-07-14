package org.jscsi.target.storage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RandomAccessStorageModuleTest {

    private static final String TEST_FILE_NAME = "storage_test_file.dat";

    private static IStorageModule module = null;

    private static final int TEST_FILE_SIZE = 262144;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        File file = new File(TEST_FILE_NAME);
        // make sure the file exists, has the correct length and is
        // accessible via module, if RandomAccessModule is chosen
        module = RandomAccessStorageModule.open(file, TEST_FILE_SIZE, true, RandomAccessStorageModule.class);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        module.close();// must be closed for delete to work
        RandomAccessStorageModule.recursiveDelete(new File(TEST_FILE_NAME));
    }

    @Test
    public void testReadAndWrite() throws IOException {

        // create and initialize arrays
        final byte[] writeArray = new byte[TEST_FILE_SIZE];
        for (int i = 0; i < TEST_FILE_SIZE; ++i) {
            writeArray[i] = (byte)(Math.random() * 256);
        }
        final byte[] readArray = new byte[TEST_FILE_SIZE];
        final ByteBuffer writeBuffer = ByteBuffer.wrap(writeArray);
        final ByteBuffer readBuffer = ByteBuffer.wrap(readArray);

        // write
        module.write(writeBuffer,// bytes (source)
            0);// storage index

        // read
        module.read(readBuffer,// bytes (destination)
            0);// storageIndex

        // check for errors
        for (int i = 0; i < TEST_FILE_SIZE; ++i)
            if (writeArray[i] != readArray[i])
                fail("values do not match");

    }

    @Test
    public void testCheckBounds0() {
        // should all be within bounds
        int result = module.checkBounds(0,// logicalBlockAddress
            2);// transferLengthInBlocks
        assertEquals(0, result);
        result = module.checkBounds(1,// logicalBlockAddress
            1);// transferLengthInBlocks
        assertEquals(0, result);
        result = module.checkBounds(1,// logicalBlockAddress
            1);// transferLengthInBlocks
        assertEquals(0, result);
        result = module.checkBounds(0,// logicalBlockAddress
            0);// transferLengthInBlocks
        assertEquals(0, result);
    }

    @Test
    public void testCheckBounds1() {
        // wrong logical block address
        int result = module.checkBounds(-1,// logicalBlockAddress
            1);// transferLengthInBlocks
        assertEquals(1, result);
        result = module.checkBounds(2,// logicalBlockAddress
            1);// transferLengthInBlocks
        assertEquals(0, result);
    }

    @Test
    public void testCheckBounds2() {
        int result = module.checkBounds(0,// logicalBlockAddress
            3);// transferLengthInBlocks
        assertEquals(0, result);
        result = module.checkBounds(0,// logicalBlockAddress
            -1);// transferLengthInBlocks
        assertEquals(2, result);
    }

    @Test
    public void testOpen() {
        // behavior to test is performed in setUpBeforeClass()
        assertTrue(module != null);
    }

}
