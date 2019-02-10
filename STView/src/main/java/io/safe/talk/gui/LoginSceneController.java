package io.safe.talk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginSceneController extends Application implements Initializable {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("gui/view/LoginScene.fxml"));


        primaryStage.setTitle("Safe Talk");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setMaxHeight(620);
        primaryStage.setWidth(650);
        primaryStage.setMaxWidth(1080);
        primaryStage.setResizable(false);


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

    }
}
