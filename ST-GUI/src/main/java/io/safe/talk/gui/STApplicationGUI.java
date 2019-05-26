package io.safe.talk.gui;

import io.safe.talk.gui.controller.HomeScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;

public class STApplicationGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(this.getClass().getClassLoader().getResource("HomeScreen.fxml"));

        Image stfLogo = new Image(this.getClass().getClassLoader().getResource("media/logo.png").toString());
        primaryStage.getIcons().add(stfLogo);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Safe Talk");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
