package io.safe.talk.core.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileManipulationUtility {

    private FileManipulationUtility() {
    }

    public static void moveToDir(String path, boolean createDir, File... files) throws IOException {

        File destinationDir = new File(path);
        for (File file : files) {
            FileUtils.moveFileToDirectory(file, destinationDir, createDir);
        }


    }

    public static void moveToFile(String destinationPath, File sourceFile) throws IOException {
        FileUtils.moveFile(sourceFile, new File(destinationPath));
    }

    public static void copyToDir(String destinationPath, File... files) throws IOException {

        File destinationDir = new File(destinationPath);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }
        for (File file : files) {
            FileUtils.copyFileToDirectory(file, destinationDir);
        }
    }

    public static boolean removeFile(File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static void removeDirectory(File file) throws IOException {
        FileUtils.deleteDirectory(file);
    }

    public static String createEmptyDir(String root, String... dirs) {
        String emptyDirPath = pathBuilder(root, dirs);
        File newDir = new File(emptyDirPath);

        if (!newDir.exists()) {
            newDir.mkdirs();
        }

        return emptyDirPath;
    }

    public static String pathBuilder(String root, String... dirs) {
        StringBuilder sb = new StringBuilder(root);

        for (String dir : dirs) {
            sb.append(File.separator);
            sb.append(dir);
        }

        return sb.toString();
    }
}
