package io.safe.talk.encryption;

import io.safe.talk.util.FileManipulationUtility;

public class Encryptable {

    private Encryptable() {
    }

    public static final String ROOT_KEY_LOCATION = FileManipulationUtility.pathBuilder(System.getProperty("user.home"), "Installs", "safe-talk");
    public static final String DEFAULT_PUBLIC_KEY_LOCATION = FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "personal", "public.key");
    public static final String DEFAULT_PRIVATE_KEY_LOCATION = FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "personal", "private.key");
    public static final String CONTACTS_LOCATION = FileManipulationUtility.pathBuilder(ROOT_KEY_LOCATION, "contacts");

    public static final int RSA_LENGTH = 2048;
    public static final int AES_LENGTH = 256;
}
