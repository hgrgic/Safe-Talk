package io.safe.talk.gui.dialogs;

import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.util.Optional;

public class KeyGenerationDialog {

    public ImportDataWrapper createDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Key Location");
        dialog.setHeaderText("Create new key pair");
        dialog.setContentText("Please enter key location name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return new ImportDataWrapper(result.get());
        }
        return null;
        //TODO: throw custom exception
    }

    public class ImportDataWrapper {
        private String keyLocation;

        public ImportDataWrapper(String keyLocation) {
            this.keyLocation = keyLocation;
        }

        public String getKeyLocation() {
            return keyLocation;
        }
    }
}
