package io.safe.talk.controller.local.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.safe.talk.model.contacts.ContactModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ContactConfiguration {

    public void exportContactConfiguration() {
        //TODO: implement config export
    }

    public List<ContactModel> importContactConfiguration(File[] files) {
        List<ContactModel> contacts = new ArrayList<>();
        try{
            for(File file: files){
                byte[] jsonData = Files.readAllBytes(Paths.get(file.getPath()));
                ObjectMapper objectMapper = new ObjectMapper();
                ContactModel contactModel = objectMapper.readValue(jsonData, ContactModel.class);
                contacts.add(contactModel);
            }
        }catch (IOException ioe){
            System.out.println(ioe.getStackTrace());
        }finally {
            return contacts;
        }
    }

    public ContactModel createNewContact(){
        //TODO: implement create contact
        return null;
    }

    public boolean deleteContact(){
        //TODO: implement delete contact
        return true;
    }
}
