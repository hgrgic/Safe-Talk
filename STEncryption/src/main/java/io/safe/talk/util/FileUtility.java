package io.safe.talk.util;

import java.io.File;

public class FileUtility {

    public static String pathBuilder(String root, String... dirs){
        StringBuilder sb = new StringBuilder(root);

        for(String dir: dirs){
            sb.append(File.separator);
            sb.append(dir);
        }

        return sb.toString();
    }
}
