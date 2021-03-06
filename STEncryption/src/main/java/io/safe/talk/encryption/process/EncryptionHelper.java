package io.safe.talk.encryption.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class EncryptionHelper {

    protected void writeToFile(File output, byte[] toWrite) throws IOException {

        try (FileOutputStream fos = new FileOutputStream(output)) {
            fos.write(toWrite);
            fos.flush();
        }
    }

    public byte[] getFileInBytes(File f) throws IOException {
        try (FileInputStream fis = new FileInputStream(f)) {
            byte[] fbytes = new byte[(int) f.length()];
            fis.read(fbytes);
            return fbytes;
        }

    }

    protected void processFile(Cipher ci, InputStream in, OutputStream out) throws IllegalBlockSizeException, BadPaddingException, IOException {

        byte[] ibuf = new byte[1024];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = ci.update(ibuf, 0, len);
            if (obuf != null) {
                out.write(obuf);
            }
        }
        byte[] obuf = ci.doFinal();
        if (obuf != null) {
            out.write(obuf);
        }
    }
}
