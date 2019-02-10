package io.safe.talk.controller.mail;
import io.safe.talk.model.mail.MailFolderModel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.*;

public class FetchMail extends MailAuth{
    private Store store;

    public FetchMail(){
        super();
    }

    public void verifyUser(String host, String storeType, String user, String password){
        try {
            Properties properties = new Properties();
            properties.put("mail.imaps.host", host);


            this.store = super.authenthicateUser(properties, user, password).getStore(storeType);
            store.connect(host, user, password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public Map<String, MailFolderModel> fetchAllMail(){
        Map<String, MailFolderModel> mailFolders = new LinkedHashMap<>();

        try {
            if(this.store.isConnected()){
                Folder[] f = this.store.getDefaultFolder().list();
                for(Folder fd:f){

                    if ((fd.getType() & Folder.HOLDS_MESSAGES) != 0){
                        fd.open(Folder.READ_ONLY);
                        Message messages[] = fd.getMessages();
                        mailFolders.put(fd.getName(), new MailFolderModel(fd.getName(), fd.getMessageCount(), messages));
                        fd.close(false);
                    }
                }

            }else {
                System.err.println("Cannot read folder metadata, store not connected");
            }

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return mailFolders;
        }
    }

    public boolean closeStoreConnection() {
        try {
            this.store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            return !this.store.isConnected();
        }
    }
}