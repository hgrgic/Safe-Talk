package io.safe.talk.gui.dialogs.simple;

import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;

public class SimpleChoiceSelectDialog {

    public String createDialog(String title, String header, String contentText, List<String> choices) {
        if(choices.size() > 0){
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(title);
            dialog.setHeaderText(header);
            dialog.setContentText(contentText);

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                return result.get();
            }
            return null;
        }
        //TODO: throw custom exception
        return null;
    }
}

