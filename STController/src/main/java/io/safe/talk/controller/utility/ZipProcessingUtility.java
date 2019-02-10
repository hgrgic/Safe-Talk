package io.safe.talk.controller.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipProcessingUtility {

    public static void startZipProcess(File srcFiles, String zipName, String zipOutput){
        String outPutZip = FileManipulationUtility.pathBuilder(zipOutput, zipName);
        try{
            FileOutputStream fos = new FileOutputStream(outPutZip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            zipFile(srcFiles, zipName.substring(0, zipName.indexOf(".")), zipOut);
            zipOut.close();
            fos.close();

            //TODO: exception handling
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
