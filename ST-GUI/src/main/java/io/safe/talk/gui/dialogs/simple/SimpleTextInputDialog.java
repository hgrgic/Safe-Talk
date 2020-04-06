package io.safe.talk.gui.dialogs.simple;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class SimpleTextInputDialog {

    public String createDialog(String title, String header, String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(contentText);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
        //TODO: throw custom exception
    }
}
