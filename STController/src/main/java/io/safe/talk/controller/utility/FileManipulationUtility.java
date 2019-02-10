package io.safe.talk.controller.utility;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileManipulationUtility {

    public static void moveToDir(String path, boolean createDir, File... files){
        try {

            File destinationDir = new File(path);
            for(File file: files){
                FileUtils.moveFileToDirectory(file, destinationDir, createDir);
                System.out.printf("File %s moved to %s\n", file.getName(), destinationDir);
            }

        } catch (IOException e) {
            //TODO: exception handling
            e.printStackTrace();
        }
    }

    public static void moveToFile(String destinationPath, File sourceFile){
        try {
            FileUtils.moveFile(sourceFile, new File(destinationPath));

            //TODO: handle exception (FileExistsException...);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyToDir(String destinationPath, File... files){
        try {

            File destinationDir = new File(destinationPath);
            if (!destinationDir.exists()){
                destinationDir.mkdirs();
            }
            for(File file: files){
                FileUtils.copyFileToDirectory(file, destinationDir);
                System.out.printf("File %s copied to %s\n", file.getName(), destinationDir);
            }

        } catch (IOException e) {
            //TODO: exception handling (File not found...);
            e.printStackTrace();
        }
    }

    public static void removeFile(File file){
        if (file.exists()){
            if(file.delete()){
                System.out.printf("File %s deleted successfully.\n", file.getName());
            }else {
                System.out.printf("Warning, file %s could not be deleted.\n", file.getName());
            }
        }
    }

    public static void removeDirectory(File file){
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            //TODO: exception handling
            e.printStackTrace();
        }finally {
            System.out.printf("Directory %s deleted successfully.\n", file.getName());
        }
    }

    public static String createEmptyDir(String root, String... dirs){
        String emptyDirPath = pathBuilder(root, dirs);

        File newDir = new File(emptyDirPath);

        if(!newDir.exists()){
            newDir.mkdirs();
        }

        System.out.printf("Created empty directory structure at: %s\n", emptyDirPath);

        return emptyDirPath;
    }

    public static String pathBuilder(String root, String... dirs){
        StringBuilder sb = new StringBuilder(root);

        for(String dir: dirs){
            sb.append(File.separator);
            sb.append(dir);
        }

        return sb.toString();
    }
}
