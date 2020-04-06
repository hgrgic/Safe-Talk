package io.safe.talk.gui.dialogs;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AboutDialog {

    public static Stage getAboutDialog() {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Safe Talk - About");

        ImageView aboutIcon = new ImageView();
        aboutIcon.setFitHeight(100);
        aboutIcon.setFitWidth(100);
        aboutIcon.setFitWidth(100);
        javafx.scene.image.Image stfLogo = new Image(AboutDialog.class.getClassLoader().getResource("media/logo.png").toString());
        aboutIcon.setImage(stfLogo);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
            aboutIcon,
            new javafx.scene.control.Label("Safe Talk - Encryption Utility App"),
            new javafx.scene.control.Label("Ver. 1.1 | cli-to-core beta")
        );

        Scene scene = new Scene(layout, 250, 200);
        dialog.setScene(scene);

        return dialog;
    }
}
