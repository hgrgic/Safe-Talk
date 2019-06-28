package io.safe.talk.gui.controller;


import io.safe.talk.cli.controller.commands.SecurityBroker;
import io.safe.talk.cli.controller.commands.SignatureBroker;
import io.safe.talk.gui.dialogs.AboutDialog;
import io.safe.talk.gui.dialogs.ConfirmationBox;
import io.safe.talk.gui.dialogs.ContactImportDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

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
    public MenuItem miSign;
    public MenuItem miVerifySig;
    public MenuItem miSharePublicKey;
    public MenuItem miImportContact;

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
                if(HomeScreenController.this.lvFiles.getItems().size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    SecurityBroker securityBroker = new SecurityBroker();

                    HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                        if (securityBroker.decryptFile(listedFiles.get(item.toString()).getAbsolutePath())) {
                            sb.append(String.format("%s decrypted\n", listedFiles.get(item.toString()).getName()));
                        }
                    });

                    ConfirmationBox.getSuccessBox("Files decrypted successfully!", sb.toString());
                } else {
                    ConfirmationBox.getFailBox("No files added in the list!");
                }
            }
        });

        btnEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if(HomeScreenController.this.lvFiles.getItems().size() > 0) {
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

                        ConfirmationBox.getSuccessBox("Files encrypted successfully!", sb.toString());
                    }
                } else {
                    ConfirmationBox.getFailBox("No files added in the list!");
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

        miSign.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if(HomeScreenController.this.lvFiles.getItems().size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    SignatureBroker signatureBroker = new SignatureBroker();

                    HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                        if (signatureBroker.digitallySignFile(listedFiles.get(item.toString()).getAbsolutePath())) {
                            sb.append(String.format("%s signed\n", listedFiles.get(item.toString()).getName()));
                        }
                    });
                    ConfirmationBox.getSuccessBox("Files signed successfully!", sb.toString());
                } else {
                    ConfirmationBox.getFailBox("No files added in the list!");
                }
            }
        });

        miVerifySig.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if(HomeScreenController.this.lvFiles.getItems().size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    SignatureBroker signatureBroker = new SignatureBroker();
                    FileChooser fileChooser = new FileChooser();

                    ConfirmationBox.getSuccessBox("", "Select Public Key");
                    fileChooser.setTitle("Select Public Key");
                    File publicKey = fileChooser.showOpenDialog(null);

                    if(publicKey != null){
                        HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                            ConfirmationBox.getSuccessBox("", "Select Signature for " + item);
                            fileChooser.setTitle("Select Signature for " + item);
                            File selectedSignature = fileChooser.showOpenDialog(null);

                            if(selectedSignature != null){
                                if (signatureBroker.verifyDigitalSignature(publicKey.getAbsolutePath(), selectedSignature.getAbsolutePath(), listedFiles.get(item.toString()).getAbsolutePath())) {
                                    sb.append(String.format("%s verified\n", listedFiles.get(item.toString()).getName()));
                                    ConfirmationBox.getSuccessBox("Success", sb.toString());
                                    return;
                                } else {
                                    sb.append(String.format("%s not valid\n", listedFiles.get(item.toString()).getName()));
                                    ConfirmationBox.getFailBox(sb.toString());
                                    return;
                                }
                            }
                        });
                    }
                } else {
                    ConfirmationBox.getFailBox("No files added in the list!");
                }
            }
        });

        miSharePublicKey.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SecurityBroker securityBroker = new SecurityBroker();
                boolean copied = securityBroker.sharePublicKey();
                if(copied){
                    ConfirmationBox.getSuccessBox("Copy success", "Public key successfully copied to clipboard!");
                } else {
                    ConfirmationBox.getFailBox("Public key could not be copied.");
                }
            }
        });

        miImportContact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ContactImportDialog contactImportDialog = new ContactImportDialog();
                ContactImportDialog.ImportDataWrapper data = contactImportDialog.createImportContactDialog();
                if(data != null){
                    SecurityBroker securityBroker = new SecurityBroker();
                    boolean imported = securityBroker.importContact(data.getPathToPublicKey(), data.getContactName());
                    if(imported){
                        ConfirmationBox.getSuccessBox("Success", "Contact imported!");
                    } else {
                        ConfirmationBox.getFailBox("Contact could not be imported!");
                    }
                }
            }
        });
    }
}
