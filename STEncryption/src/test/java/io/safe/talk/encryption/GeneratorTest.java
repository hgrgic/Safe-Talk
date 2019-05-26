package io.safe.talk.encryption;


import io.safe.talk.encryption.generator.SecreteKeyGenerator;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class GeneratorTest {

    @Test
    public void rsaKeyGeneration(){
            SecreteKeyGenerator gk;
            try {
                gk = new SecreteKeyGenerator();
                gk.createRSAKeys();
                gk.writeToFile("/Users/HrvojeGrgic/Desktop/Enc/publicKey", gk.getPublicKey().getEncoded());
                gk.writeToFile("/Users/HrvojeGrgic/Desktop/Enc/privateKey", gk.getPrivateKey().getEncoded());
            } catch (NoSuchAlgorithmException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
    }
}
