package com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.StrokeType;

import java.io.IOException;

/**
 * Created by Roy on 08.12.13.
 */
public class TrackComponent extends HBox {

    @FXML private HBox trackHBox;
    @FXML private ImageView instrumentIcon;
    @FXML private CheckBox trackActivated;
    @FXML private HBox trackBeats;

    //instrument, name, and length muss übergeben werden.
    public TrackComponent(String name, String instrument, int songLength, int bpm) {
        FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("/com/track.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        //call setBeats;


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setBeats(songLength, bpm);
    }

    public ImageView getInstrumentIcon() {
        return instrumentIcon;
    }

    public void setInstrumentIcon(ImageView newInstrumentIcon) {
        instrumentIcon = newInstrumentIcon;
    }

    public CheckBox getTrackActivated() {
        return trackActivated;
    }

    public HBox getTrackBeats() {
        return trackBeats;
    }

    public void setBeats(double songLenght, int bpm) {
        double numberOfBeats = (songLenght/1000)/60 * bpm;

        for(int i = 0; i< numberOfBeats; i++) {
            Rectangle rect = RectangleBuilder.create()
                    .arcHeight(0).arcWidth(0)
                    .width(8).height(55)
                    .fill(Color.web("#000833"))
                    .strokeWidth(1).stroke(Color.BLACK).strokeType(StrokeType.INSIDE)
                    .id(String.valueOf(i))
                    .build();
            trackBeats.getChildren().add((Node) rect);
        }

    }

    public void updateBeats(double songLenght, int bpm) {
        int oldNumberOfBeats = trackBeats.getChildren().size();
        double numberOfBeats = Math.abs(((songLenght/1000)/60 * bpm) - oldNumberOfBeats);

        for(int i = 0; i< numberOfBeats; i++) {
            Rectangle rect = RectangleBuilder.create()
                    .arcHeight(0).arcWidth(0)
                    .width(8).height(55)
                    .fill(Color.web("#000833"))
                    .strokeWidth(1).stroke(Color.BLACK).strokeType(StrokeType.INSIDE)
                    .id(String.valueOf(i+oldNumberOfBeats))
                    .build();
            trackBeats.getChildren().add((Node) rect);
        }
    }


}
