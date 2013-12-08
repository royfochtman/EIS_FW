package com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by Roy on 08.12.13.
 */
public class TrackComponent extends HBox {

    @FXML
    private HBox trackHBox;

    public TrackComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("/com/track.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
