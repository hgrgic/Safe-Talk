package io.safe.talk.core.commands.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.safe.talk.core.commands.Executable;
import org.junit.Test;

import java.io.File;

public class DecryptCommandTest {

    @Test
    public void testDecryptionProcess() {
        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("test-image.png").getFile());
        File testPublicKey = new File(classLoader.getResource("test-public.key").getFile());
        File testPrivateKey = new File(classLoader.getResource("test-private.key").getFile());

        if(testFile.exists() && testPublicKey.exists() && testPrivateKey.exists()){
            Executable encryptionCommand = new EncryptCommand(testFile.getAbsolutePath(), testPublicKey.getAbsolutePath());
            assertTrue(encryptionCommand.execute());

            File encryptedFileCreatedByTest = new File(classLoader.getResource("test-image.png.enc").getFile());
            assertTrue(encryptedFileCreatedByTest.exists());

            Executable decryptionCommand = new DecryptCommand(encryptedFileCreatedByTest.getAbsolutePath(),
                                                             testPrivateKey.getAbsolutePath());

            assertTrue(decryptionCommand.execute());
            assertTrue(encryptedFileCreatedByTest.delete());
            assertFalse(encryptedFileCreatedByTest.exists());

        } else {
            throw new RuntimeException("Test resource does not exist");
        }
    }
}
