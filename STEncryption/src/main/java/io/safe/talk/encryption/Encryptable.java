package io.safe.talk.encryption;

import io.safe.talk.exceptions.KeyGenerationException;
import io.safe.talk.util.FileManipulationUtility;

public final class Encryptable {

    private Encryptable() {
    }

    public static final String ROOT_KEY_LOCATION = FileManipulationUtility.pathBuilder(System.getProperty("user.home"), "Installs", "safe-talk");
    public static final String DEFAULT_PUBLIC_KEY_LOCATION = FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "personal", "public.key");
    public static final String DEFAULT_PRIVATE_KEY_LOCATION = FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "personal", "private.key");
    public static final String CONTACTS_LOCATION = FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "contacts");
    public static final String PERSONAL_KEY_LOCATIONS = FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "personal");

    public static final int RSA_LENGTH = 2048;
    public static final int AES_LENGTH = 256;

    public static String generateCustomPublicKeyLocation(String keyName) {
        if (keyName.trim().length() > 0) {
            return FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "personal", keyName, "public.key");
        }

        throw new KeyGenerationException("Key name cannot be empty");
    }

    public static String generateCustomPrivateKeyLocation(String keyName) {
        if (keyName.trim().length() > 0) {
            return FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "personal", keyName, "private.key");
        }

        throw new KeyGenerationException("Key name cannot be empty");
    }
}
