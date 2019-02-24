package io.safe.talk.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipProcessingUtility {

    private ZipProcessingUtility(){}

    public static void startZipProcess(File srcFiles, String zipName, String zipOutput) throws IOException {
        String outPutZip = FileManipulationUtility.pathBuilder(zipOutput, zipName);
            FileOutputStream fos = new FileOutputStream(outPutZip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            zipFile(srcFiles, zipName.substring(0, zipName.indexOf('.')), zipOut);
            zipOut.close();
            fos.close();
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        try(FileInputStream fis = new FileInputStream(fileToZip)){
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
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }
}
