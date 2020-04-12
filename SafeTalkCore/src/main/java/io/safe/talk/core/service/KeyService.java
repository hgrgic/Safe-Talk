package io.safe.talk.core.service;

import io.safe.talk.encryption.Encryptable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KeyService {

    public static List<String> listPrivateKeyDirectories() throws NullPointerException{
        return KeyService.listKeyDirectories(Encryptable.PERSONAL_KEY_LOCATIONS);
    }

    public static List<String> listContactsKeyDirectories() throws NullPointerException{
        return KeyService.listKeyDirectories(Encryptable.CONTACTS_LOCATION);
    }

    private static List<String> listKeyDirectories(String path) throws NullPointerException{
        List<String> entities = new ArrayList<>();
        File root = new File(path);

        for(File file: root.listFiles()){
            if(file.isDirectory()){
                entities.add(file.getName());
            }
        }
        return entities;
    }
}
