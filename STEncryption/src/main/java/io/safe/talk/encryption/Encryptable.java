package io.safe.talk.encryption;

import io.safe.talk.util.FileUtility;

public interface Encryptable {

    String ROOT_KEY_LOCATION = FileUtility.pathBuilder(System.getProperty("user.home"),
            "Installs", "safe-talk");
    String PUBLIC_KEY_LOCATION = FileUtility.pathBuilder(ROOT_KEY_LOCATION, "personal","publicKey");
    String PRIVATE_KEY_LOCATION = FileUtility.pathBuilder(ROOT_KEY_LOCATION, "personal","privateKey");

    int RSA_LENGTH = 3072;
    int AES_LENGTH = 256;
}
