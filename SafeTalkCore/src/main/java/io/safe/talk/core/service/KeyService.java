package io.safe.talk.core.service;

import io.safe.talk.encryption.Encryptable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KeyService {

    public static List<String> listPrivateKeyDirectories(){
        return KeyService.listDirectories(Encryptable.PERSONAL_KEY_LOCATIONS);
    }

    public static List<String> listContactsKeyDirectories(){
        return KeyService.listDirectories(Encryptable.CONTACTS_LOCATION);
    }

    private static List<String> listDirectories(String path){
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
