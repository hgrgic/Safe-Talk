package io.safe.talk.core.service;

import io.safe.talk.core.security.encryption.Encryptable;
import io.safe.talk.core.util.FileManipulationUtility;

import java.util.List;

public class KeyService {

    public static List<String> listPrivateKeyDirectories() throws NullPointerException{
        return FileManipulationUtility.listDirectories(Encryptable.PERSONAL_KEY_LOCATIONS);
    }

    public static List<String> listContactsKeyDirectories() throws NullPointerException{
        return FileManipulationUtility.listDirectories(Encryptable.CONTACTS_LOCATION);
    }
}
