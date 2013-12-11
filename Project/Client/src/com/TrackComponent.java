package com;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    @FXML private ImageView imageViewInstrument;
    @FXML private Label labelName;
    @FXML private CheckBox trackActivated;
    @FXML private HBox trackBeats;

    private int bpm = 0;

    //instrument, name, and length muss übergeben werden.
    public TrackComponent(String name, String instrument, int songLength, int bpm) {
        FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("/com/track.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setId(name + "TrackComponent");
        if(name != null) {
            labelName.setText(name);
        }

        setBeats(songLength, bpm);
    }

    public ImageView getInstrumentIcon() {
        return imageViewInstrument;
    }

    public void setInstrumentIcon(ImageView newInstrumentIcon) {
        imageViewInstrument = newInstrumentIcon;
    }

    public HBox getTrackBeats() {
        return trackBeats;
    }

    public void setBeats(double songLength, int bpm) {
        double numberOfBeats = (songLength/1000)/60 * bpm;

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

    public void updateBeats(double songLength, int bpm) {
        int oldNumberOfBeats = trackBeats.getChildren().size();
        double numberOfBeats = Math.abs(((songLength/1000)/60 * bpm) - oldNumberOfBeats);

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

    public String getName() {
        return labelName.getText();
    }

    public void setName(String name) {
        labelName.setText(name);
    }

    public boolean isActivated() {
        return trackActivated.isSelected();
    }

    public void setInstrument() {
        //setinstrument
    }

    //get instrument

}
