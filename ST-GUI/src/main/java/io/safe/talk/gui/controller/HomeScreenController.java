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
import java.util.*;


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
                StringBuilder sb = new StringBuilder();
                SecurityBroker securityBroker = new SecurityBroker();

                HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                    if (securityBroker.decryptFile(listedFiles.get(item.toString()).getAbsolutePath())) {
                        sb.append(String.format("%s decrypted\n", listedFiles.get(item.toString()).getName()));
                    }
                });

                HomeScreenController.this.lvFiles.getItems().clear();
                HomeScreenController.this.listedFiles.clear();
                ConfirmationBox.getSuccessBox("Files decrypted successfully!", sb.toString());

            }
        });

        btnEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                StringBuilder sb = new StringBuilder();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Public Key");
                File selectedFile = fileChooser.showOpenDialog(null);

                if(selectedFile != null){
                    SecurityBroker securityBroker = new SecurityBroker();
                    HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                        if (securityBroker.encryptFile(listedFiles.get(item.toString()).getAbsolutePath(), selectedFile.getAbsolutePath())) {
                            sb.append(String.format("%s encrypted\n", listedFiles.get(item.toString()).getName()));
                        }
                    });

                    HomeScreenController.this.lvFiles.getItems().clear();
                    HomeScreenController.this.listedFiles.clear();
                    ConfirmationBox.getSuccessBox("Files encrypted successfully!", sb.toString());
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
                    if (new SecurityBroker().generateKeys()) {
                        ConfirmationBox.getSuccessBox("Keys generated successfully!", null);
                    }
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
