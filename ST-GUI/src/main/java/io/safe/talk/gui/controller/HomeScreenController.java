package io.safe.talk.gui.controller;

import io.safe.talk.core.commands.SecurityBroker;
import io.safe.talk.core.commands.SignatureBroker;
import io.safe.talk.core.exceptions.CriticalCommandException;
import io.safe.talk.core.exceptions.DestinationDirectoryException;
import io.safe.talk.core.exceptions.FileManipulationException;
import io.safe.talk.core.logger.ErrorLogger;
import io.safe.talk.core.service.KeyService;
import io.safe.talk.core.security.encryption.Encryptable;
import io.safe.talk.gui.dialogs.AboutDialog;
import io.safe.talk.gui.dialogs.ConfirmationBox;
import io.safe.talk.gui.dialogs.ContactImportDialog;
import io.safe.talk.gui.dialogs.simple.SimpleChoiceSelectDialog;
import io.safe.talk.gui.dialogs.simple.SimpleTextInputDialog;
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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;

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

    public HomeScreenController() {
        listedFiles = new HashMap<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddFiles.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.setTitle("Select File");

                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    listedFiles.put(selectedFile.getPath(), selectedFile);
                    lvFiles.getItems().add(selectedFile.getPath());
                }
            }
        });

        btnDecrypt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (HomeScreenController.this.lvFiles.getItems().size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    SecurityBroker securityBroker = new SecurityBroker();

                    try {
                        SimpleChoiceSelectDialog choiceSelectDialog = new SimpleChoiceSelectDialog();
                        String selectedKey = choiceSelectDialog.createDialog("Key Selection",
                                                                             "Select Private Key",
                                                                             "Private keys:", KeyService.listPrivateKeyDirectories());
                        if(selectedKey != null){
                            HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                                try {
                                    if (securityBroker.decryptFile(listedFiles.get(item.toString()).getAbsolutePath(), selectedKey)) {
                                        sb.append(String.format("%s decrypted\n", listedFiles.get(item.toString()).getName()));
                                    }
                                } catch (CriticalCommandException cce) {
                                    throw cce;
                                }
                            });
                        }
                        ConfirmationBox.getSuccessBox("Files decrypted successfully!", sb.toString());
                    }catch (NullPointerException npe) {
                        ConfirmationBox.getFailBox("Decryption failed!", "Check that necessary application file structure exists. " +
                            "\nIt is possible you did not generate any personal key-pair.");
                        return;
                    }
                    catch (Exception e) {
                        ErrorLogger.getLogger().log(Level.SEVERE, "Decryption failed", e);
                        ConfirmationBox.getFailBox("Decryption failed!", e.getLocalizedMessage());
                        return;
                    }
                } else {
                    ConfirmationBox.getFailBox("No files added in the list!");
                }
            }
        });

        btnEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (HomeScreenController.this.lvFiles.getItems().size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select Public Key");
                    File contactsFile = new File(Encryptable.CONTACTS_LOCATION);
                    if(contactsFile.exists()) fileChooser.setInitialDirectory(contactsFile);

                    File selectedFile = fileChooser.showOpenDialog(null);

                    if (selectedFile != null) {
                        SecurityBroker securityBroker = new SecurityBroker();
                        try {
                            HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                                try {
                                    if (securityBroker.encryptFile(listedFiles.get(item.toString()).getAbsolutePath(), selectedFile.getAbsolutePath())) {
                                        sb.append(String.format("%s encrypted\n", listedFiles.get(item.toString()).getName()));
                                    }
                                } catch (CriticalCommandException cce) {
                                    throw cce;
                                }
                            });
                        } catch (Exception e) {
                            ErrorLogger.getLogger().log(Level.SEVERE, "Encryption failed", e);
                            ConfirmationBox.getFailBox("Encryption failed!", e.getLocalizedMessage());
                            return;
                        }
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
                SimpleTextInputDialog keyGenerationDialog = new SimpleTextInputDialog();
                String data = keyGenerationDialog.createDialog("Key Creation Dialog",
                                                               "Create new key-pair",
                                                               "Key-pair name:");

                if (data != null) {
                    SecurityBroker securityBroker = new SecurityBroker();
                    try {
                        if(securityBroker.inspectKeysLocation(data)){
                            if (securityBroker.generateKeys(data)) {
                                ConfirmationBox.getSuccessBox("Keys generated successfully!", null);
                            }
                        }

                    } catch (FileManipulationException fileAlreadyExists){
                        boolean overrideGenerateWarning = ConfirmationBox.getConfirmationBox("Old keys will be overwritten, and files encrypted with previous keys will no longer be accessible!",
                                                                                     fileAlreadyExists.getLocalizedMessage());
                        if(overrideGenerateWarning){
                            if (securityBroker.generateKeys(data)) {
                                ConfirmationBox.getSuccessBox("Keys generated successfully!", null);
                            }
                        }
                    }
                    catch (CriticalCommandException e) {
                        ErrorLogger.getLogger().log(Level.SEVERE, "Key generation failed", e);
                        ConfirmationBox.getFailBox("Security keys could be generated!");
                        return;
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
                try{
                    if (HomeScreenController.this.lvFiles.getItems().size() > 0) {

                        SimpleChoiceSelectDialog choiceSelectDialog = new SimpleChoiceSelectDialog();
                        String selectedPrivateKey = choiceSelectDialog.createDialog("Sign Files",
                                                                                    "Select Private Key",
                                                                                    "Private keys:", KeyService.listPrivateKeyDirectories());
                        if(selectedPrivateKey != null){
                            StringBuilder sb = new StringBuilder();
                            SignatureBroker signatureBroker = new SignatureBroker();

                            HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                                if (signatureBroker.digitallySignFile(listedFiles.get(item.toString()).getAbsolutePath(), selectedPrivateKey)) {
                                    sb.append(String.format("%s signed\n", listedFiles.get(item.toString()).getName()));
                                }
                            });
                            ConfirmationBox.getSuccessBox("Files signed successfully!", sb.toString());
                        }
                    } else {
                        ConfirmationBox.getFailBox("No files added in the list!");
                    }
                }catch (NullPointerException npe){
                    ConfirmationBox.getFailBox("Signature procedure failed!", "Check that necessary application file structure exists. " +
                        "\nIt is possible that you did not generate any personal key-pair.");
                }
            }
        });

        miVerifySig.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (HomeScreenController.this.lvFiles.getItems().size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    SignatureBroker signatureBroker = new SignatureBroker();
                    FileChooser fileChooser = new FileChooser();

                    ConfirmationBox.getSuccessBox("", "Select Public Key");
                    fileChooser.setTitle("Select Public Key");
                    File contactsFile = new File(Encryptable.CONTACTS_LOCATION);
                    if(contactsFile.exists()) fileChooser.setInitialDirectory(contactsFile);
                    File publicKey = fileChooser.showOpenDialog(null);

                    if (publicKey != null) {
                        HomeScreenController.this.lvFiles.getItems().forEach(item -> {
                            ConfirmationBox.getSuccessBox("", "Select Signature for " + item);
                            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                            fileChooser.setTitle("Select Signature for " + item);
                            File selectedSignature = fileChooser.showOpenDialog(null);

                            if (selectedSignature != null) {
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
                try {
                    SimpleChoiceSelectDialog choiceSelectDialog = new SimpleChoiceSelectDialog();
                    String selectedKey = choiceSelectDialog.createDialog("Key Selection",
                                                                         "Select Private Key",
                                                                         "Private keys:", KeyService.listPrivateKeyDirectories());
                    if(selectedKey != null){
                        SecurityBroker securityBroker = new SecurityBroker();
                        securityBroker.sharePublicKey(selectedKey);
                        ConfirmationBox.getSuccessBox("Copy success!", "Public key successfully copied to clipboard!");
                    }
                } catch (DestinationDirectoryException e) {
                    ConfirmationBox.getFailBox("Share key failed!", e.getLocalizedMessage());
                } catch (NullPointerException npe){
                    ConfirmationBox.getFailBox("Cannot share keys!", "Check that necessary application file structure exists. " +
                        "\nIt is possible that you did not generate any personal key-pair.");
                }


            }
        });

        miImportContact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ContactImportDialog contactImportDialog = new ContactImportDialog();
                ContactImportDialog.ImportDataWrapper data = contactImportDialog.createImportContactDialog();
                if (data != null) {
                    try {
                        SecurityBroker securityBroker = new SecurityBroker();
                        securityBroker.importContact(data.getPathToPublicKey(), data.getContactName());
                        ConfirmationBox.getSuccessBox("Success", "Contact imported!");
                    } catch (FileManipulationException e) {
                        ConfirmationBox.getFailBox("Contact import failed!", e.getLocalizedMessage());
                    }

                }
            }
        });
    }
}
