package io.safe.talk.core.security.encryption;

import io.safe.talk.core.exceptions.KeyGenerationException;
import io.safe.talk.core.util.FileManipulationUtility;

public final class Encryptable {

    private Encryptable() {
    }

    public static final String ROOT_KEY_LOCATION = FileManipulationUtility.pathBuilder(System.getProperty("user.home"), "Installs", "safe-talk");
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

    public static String generateCustomContactPublicKeyLocation(String contactName) {
        if (contactName.trim().length() > 0) {
            return FileManipulationUtility.pathBuilder(CONTACTS_LOCATION, contactName, "public.key");
        }
        throw new KeyGenerationException("Contact name cannot be empty");
    }
}
