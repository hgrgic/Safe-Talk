package io.safe.talk.controller.local;

import io.safe.talk.controller.adapters.EncryptionAdapter;
import io.safe.talk.controller.local.configurations.ContactConfiguration;
import io.safe.talk.controller.utility.FileManipulationUtility;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.generator.SecreteKeyGenerator;
import io.safe.talk.model.contacts.ContactModel;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

public class HomeManager implements Encryptable {

    public void checkPersonalConfiguration() {
        File homeRoot = new File(Encryptable.ROOT_KEY_LOCATION);
        if (!homeRoot.exists()) {
            homeRoot.mkdirs();

            SecreteKeyGenerator gk;
            try {
                gk = new SecreteKeyGenerator();
                gk.createRSAKeys();
                gk.writeToFile(Encryptable.PUBLIC_KEY_LOCATION, gk.getPublicKey().getEncoded());
                gk.writeToFile(Encryptable.PRIVATE_KEY_LOCATION, gk.getPrivateKey().getEncoded());
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ContactModel> checkContactConfiguration() {
        ContactConfiguration contactConfiguration = new ContactConfiguration();
        File publicRoot = new File(FileManipulationUtility.pathBuilder(
                Encryptable.ROOT_KEY_LOCATION, "contacts"));
        if (!publicRoot.exists()) {
            publicRoot.mkdirs();
        }
        return contactConfiguration.importContactConfiguration(publicRoot.listFiles());
    }

    public void startEncryptionProcess(ContactModel contact, File[] encryptionTargets) {
        List<File> files = new ArrayList<>();
        try {
            EncryptionAdapter adapter = new EncryptionAdapter();
            for (File target : encryptionTargets) {
                adapter.encryptFile(target, contact.getPublicKey());
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //TODO: write  to file / zip
        }
    }

    public void startDecryptionProcess(File[] decryptionTargets){
        try {
            EncryptionAdapter adapter = new EncryptionAdapter();
            for (File target : decryptionTargets) {
                adapter.decryptFile(target);
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //TODO: write  to file / zip
        }
    }
}
