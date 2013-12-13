package com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by rfochtmann on 13.12.13.
 */
public class MusicSegmentComponent extends AnchorPane {

    @FXML Rectangle musicSegmentRectangle;
    @FXML Label musicSegmentName;
    @FXML HBox musicSegmentActions;


    public MusicSegmentComponent(String name, float length, double width) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/musicSegment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setId(name + "MusicSegmentComponent");
        if(name != null) {
            musicSegmentName.setText(" (" + length + " ms)");
        }
        musicSegmentRectangle.setWidth(width);

    }



}
