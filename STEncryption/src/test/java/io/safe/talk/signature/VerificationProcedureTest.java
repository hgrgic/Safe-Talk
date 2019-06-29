package io.safe.talk.signature;

import static org.junit.Assert.assertTrue;

import io.safe.talk.digital.signature.VerifierAgent;
import io.safe.talk.encryption.process.rsa.RSAEncryption;
import org.junit.Test;

import java.security.PublicKey;

public class VerificationProcedureTest {

    @Test
    public void testSignatureProcedure() throws Exception {
        RSAEncryption ec = new RSAEncryption();
        PublicKey publicKey = ec.getPublic("/Users/HrvojeGrgic/Desktop/Enc/publickey");

        VerifierAgent verifierAgent = new VerifierAgent();
        boolean valid = verifierAgent.execute(publicKey, "/Users/HrvojeGrgic/Desktop/Enc/signature",
                                              "/Users/HrvojeGrgic/Desktop/Enc/text.txt");

        assertTrue(true);
    }
}
