package io.safe.talk.encryption;

import io.safe.talk.util.FileUtility;

public class Encryptable {

    private Encryptable(){}

    public static final String ROOT_KEY_LOCATION = FileUtility.pathBuilder(System.getProperty("user.home"),"Installs", "safe-talk");
    public static final String PUBLIC_KEY_LOCATION = FileUtility.pathBuilder(ROOT_KEY_LOCATION, "personal","publicKey");
    public static final String PRIVATE_KEY_LOCATION = FileUtility.pathBuilder(ROOT_KEY_LOCATION, "personal","privateKey");

    public static final int RSA_LENGTH = 3072;
    public static final int AES_LENGTH = 256;
}
