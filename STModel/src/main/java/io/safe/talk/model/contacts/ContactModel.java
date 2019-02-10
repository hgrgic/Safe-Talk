package io.safe.talk.model.contacts;

import lombok.NoArgsConstructor;

import java.io.File;

@NoArgsConstructor
public class ContactModel {

    private String alias;
    private String email;
    private File publicKey;

    public ContactModel(String name, String email, File publicKey) {
        this.alias = name;
        this.email = email;
        this.publicKey = publicKey;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public File getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(File publicKey) {
        this.publicKey = publicKey;
    }
}
