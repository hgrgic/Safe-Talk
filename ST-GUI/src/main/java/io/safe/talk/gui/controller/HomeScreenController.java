package io.safe.talk.gui.controller;


import io.safe.talk.cli.controller.commands.SecurityBroker;
import io.safe.talk.gui.util.AboutDialog;
import io.safe.talk.gui.util.ConfirmationBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class HomeScreenController implements Initializable {
    public Button btnAddFiles;
    public Button btnDecrypt;
    public Button btnEncrypt;
    public Button btnClearFiles;
    public ListView lvFiles;
    public MenuItem miGenerateKeys;
    public MenuItem miQuit;
    public MenuItem miAbout;

    private Map<String, File> listedFiles;

    public HomeScreenController(){
        listedFiles = new HashMap<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddFiles.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select File");

                File selectedFile = fileChooser.showOpenDialog(null);
                if(selectedFile != null){
                    listedFiles.put(selectedFile.getPath(), selectedFile);
                    lvFiles.getItems().add(selectedFile.getPath());
                }
            }
        });

        btnDecrypt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                SecurityBroker securityBroker = new SecurityBroker();

                HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                    securityBroker.decryptFile(listedFiles.get(item.toString()).getAbsolutePath());
                });

                HomeScreenController.this.lvFiles.getItems().clear();
                HomeScreenController.this.listedFiles.clear();
            }
        });

        btnEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Public Key");
                File selectedFile = fileChooser.showOpenDialog(null);

                if(selectedFile != null){
                    SecurityBroker securityBroker = new SecurityBroker();
                    HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                        securityBroker.encryptFile(listedFiles.get(item.toString()).getAbsolutePath(),
                                selectedFile.getAbsolutePath());
                    });

                    HomeScreenController.this.lvFiles.getItems().clear();
                    HomeScreenController.this.listedFiles.clear();
                }
            }
        });

        btnClearFiles.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                HomeScreenController.this.lvFiles.getItems().clear();
                HomeScreenController.this.listedFiles.clear();
            }
        });

        miGenerateKeys.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                boolean isGenerate = ConfirmationBox.getConfirmationBox("Are you sure you want to generate " +
                        "new set of public / private keys? \nOld keys will be overwritten, and " +
                        "files encrypted with previous keys will no longer be accessible!");
                if(isGenerate){
                    new SecurityBroker().generateKeys();
                }
            }
        });

        miQuit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Platform.exit();
            }
        });

        miAbout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                AboutDialog.getAboutDialog().show();
            }
        });
    }
}
