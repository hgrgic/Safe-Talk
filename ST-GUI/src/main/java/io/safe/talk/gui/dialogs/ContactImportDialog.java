package io.safe.talk.gui.dialogs;

import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.util.Optional;

public class ContactImportDialog {

    public ImportDataWrapper createImportContactDialog(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Contact Alias");
        dialog.setHeaderText("Create new contact");
        dialog.setContentText("Please enter contact alias/name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            ConfirmationBox.getSuccessBox("Public Key", "Select public key for " + result.get());
            FileChooser fileChooser = new FileChooser();
            String pathToPublicKey = fileChooser.showOpenDialog(null).getAbsolutePath();

            return new ImportDataWrapper(result.get(), pathToPublicKey);
        }
        return null;
        //TODO: throw custom exception
    }

    public class ImportDataWrapper{
        private String contactName;
        private String pathToPublicKey;

        public ImportDataWrapper(String contactName, String pathToPublicKey) {
            this.contactName = contactName;
            this.pathToPublicKey = pathToPublicKey;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getPathToPublicKey() {
            return pathToPublicKey;
        }

        public void setPathToPublicKey(String pathToPublicKey) {
            this.pathToPublicKey = pathToPublicKey;
        }
    }
}
