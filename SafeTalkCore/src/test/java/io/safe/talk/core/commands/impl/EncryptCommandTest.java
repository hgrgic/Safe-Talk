package io.safe.talk.core.commands.impl;

import static org.junit.Assert.*;

import io.safe.talk.core.commands.Executable;
import org.junit.Test;

import java.io.File;

public class EncryptCommandTest {

    @Test
    public void testEncryptionProcess() {
        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("test-image.png").getFile());
        File testPublicKey = new File(classLoader.getResource("test-public.key").getFile());

        if(testFile.exists()){
            Executable encryptionCommand = new EncryptCommand(testFile.getAbsolutePath(), testPublicKey.getAbsolutePath());
            boolean success = encryptionCommand.execute();

            File encryptedFileCreatedByTest = new File(classLoader.getResource("test-image.png.enc").getFile());
            assertTrue(success);
            assertTrue(encryptedFileCreatedByTest.exists());
            assertTrue(encryptedFileCreatedByTest.delete());
            assertFalse(encryptedFileCreatedByTest.exists());
        } else {
            throw new RuntimeException("Test resource does not exist");
        }
    }
}