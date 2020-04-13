package io.safe.talk.core.service;

import io.safe.talk.core.security.encryption.Encryptable;
import io.safe.talk.core.util.FileManipulationUtility;

import java.util.List;

public class ContactService {
    public static List<String> listContactDirectories() throws NullPointerException{
        return FileManipulationUtility.listDirectories(Encryptable.CONTACTS_LOCATION);
    }
}
