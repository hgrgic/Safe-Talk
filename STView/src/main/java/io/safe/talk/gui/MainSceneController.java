package io.safe.talk.gui;

import com.jfoenix.controls.JFXChipView;
import io.safe.talk.controller.local.HomeManager;
import io.safe.talk.model.contacts.ContactModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainSceneController extends Application implements Initializable {

    public JFXChipView chipView;
    public ListView lvInstances;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("gui/view/MainScene.fxml"));


        primaryStage.setTitle("Safe Talk Dev");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setMaxHeight(620);
        primaryStage.setWidth(1080);
        primaryStage.setMaxWidth(1080);
        primaryStage.setResizable(true);


        if(System.getProperty("os.name").contains("Mac")){
            URL iconURL = MainSceneController.class.getResource("/media/logo.png");
            Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        }else{
            //TODO: implement image for other OSs
        }


        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing System");
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HomeManager homeManager = new HomeManager();
        homeManager.checkPersonalConfiguration();
        List<ContactModel> publicContacts = homeManager.checkContactConfiguration();

        for(ContactModel contact: publicContacts){
            lvInstances.getItems().add(contact.getAlias());
        }
    }

    public void selectContact(MouseEvent mouseEvent) {
        if(lvInstances.getSelectionModel().getSelectedItem() != null){
            chipView.getChips().add(lvInstances.getSelectionModel().getSelectedItem());
        }
    }

//    public void openFileSelector(){
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Resource File");
//        fileChooser.showOpenDialog();
//    }
}
