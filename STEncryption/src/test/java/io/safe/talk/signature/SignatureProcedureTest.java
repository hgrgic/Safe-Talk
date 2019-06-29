package io.safe.talk.signature;

import static junit.framework.TestCase.assertTrue;

import io.safe.talk.digital.signature.SignatureAgent;
import io.safe.talk.encryption.process.rsa.RSADecryption;
import org.junit.Test;

import java.security.PrivateKey;

public class SignatureProcedureTest {

    @Test
    public void testSignatureProcedure() throws Exception {
        RSADecryption dc = new RSADecryption();
        PrivateKey privateKey = dc.getPrivate("/Users/HrvojeGrgic/Desktop/Enc/privateKey");

        SignatureAgent signatureAgent = new SignatureAgent();
        signatureAgent.execute(privateKey, "/Users/HrvojeGrgic/Desktop/Enc/text.txt",
                               "/Users/HrvojeGrgic/Desktop/Enc/signature");

        assertTrue(true);
    }
}
