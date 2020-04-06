package io.safe.talk.core.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class ZipUtility {
    public static void zipFiles(String... files) throws IOException {
        FileOutputStream fos = new FileOutputStream("secured.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (String file: files){

        }
        zipOut.close();
        fos.close();
    }

    public static void unzipFiles(String zipFile) throws IOException {
        //TODO: unzip files procedure
        //https://www.baeldung.com/java-compress-and-uncompress
    }


    public static void main(String [] args){
        int [][] matrix = new int [6][6];
        System.out.println(matrix[2][2]);
    }
}
