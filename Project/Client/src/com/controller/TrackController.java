package com.controller;

import com.model.TrackModel;
import com.musicbox.util.Instrument;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.StrokeType;

import java.io.File;
import java.io.IOException;

/**
 * Created by Roy on 08.12.13.
 */
public class TrackController extends HBox {

    @FXML private HBox trackHBox;
    @FXML private ImageView imageViewInstrument;
    @FXML private Label labelName;
    @FXML private CheckBox trackActivated;
    @FXML private HBox trackBeats;
    @FXML private AnchorPane anchorPaneMusicSegmentsInTrack;

    private int bpm = 0;
    private Rectangle rect;
    private final String DARK_BLUE = "#000833";
    private final String HELL_BLUE = "#002966";
    private String actualColor = "";
    private Image instrumentImage;

    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<Instrument> instrument = new SimpleObjectProperty<Instrument>();

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObjectProperty instrumentProperty() {
        return instrument;
    }

    public ObjectProperty getInstrument() {
        return this.instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument.set(instrument);
    }


    /**
     *
     * @param name
     * @param instrument
     * @param songLength
     * @param bpm
     */
    public TrackController(String name, Instrument instrument, Long songLength, int bpm) {
        FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("/com/view/track.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        TrackModel trackModel = new TrackModel(1 , null, instrument, 1, name,  songLength);

        //Bind Name Property
        this.nameProperty().bindBidirectional(trackModel.nameProperty());
        labelName.textProperty().bindBidirectional(this.nameProperty());

        //Bind Instrument Property
        //this.instrumentProperty().bindBidirectional(trackModel.);

        this.setId(name + "TrackComponent");
        if(name != null) {
            labelName.setText(name);
        }

        setBeats(songLength, bpm);
        if(name != null) {
            setName(name);
        }

        setImage(instrument);
        setInstrumentIcon(instrumentImage);

        /*this.setName("Bla bla");
        this.setName("Another Name");*/


    }

    public ImageView getInstrumentIcon() {
        return imageViewInstrument;
    }

    public void setInstrumentIcon(Image image) {
        imageViewInstrument.setImage(image);
    }

    public HBox getTrackBeats() {
        return trackBeats;
    }

    public void setBeats(double songLength, int bpm) {
        double numberOfBeats = (songLength/1000)/60 * bpm;

        for(int i = 0; i< numberOfBeats; i++) {
            String color = null;
            if(i % 4 == 0) {
                color = DARK_BLUE;
            } else color = HELL_BLUE;
            final Rectangle rect = RectangleBuilder.create()
                    .arcHeight(0).arcWidth(0)
                    .width(8).height(55)
                    .fill(Color.web(color))
                    .strokeWidth(1).stroke(Color.BLACK).strokeType(StrokeType.INSIDE)
                    .id(getName().get() + String.valueOf(i))
                    .build();

            /*Source from Drag & Drop*/
            rect.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node
                 * and if it has a string data */
                    if (event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }

                    event.consume();
                }
            });

            rect.setOnDragEntered(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* the drag-and-drop gesture entered the target */
                    /* show to the user that it is an actual gesture target */
                    if (event.getDragboard().hasString()) {
                        actualColor = rect.getFill().toString();
                        rect.setFill(Color.INDIANRED);
                    }
                    event.consume();
                }
            });

            rect.setOnDragExited(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    rect.setFill(Color.web(actualColor));
                dragEvent.consume();
                }
            });

            rect.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                    System.out.println("Rect id: " +rect.getId());
                /* if the data was successfully moved, clear it */
                    /*if (event.getTransferMode() == TransferMode.MOVE) {
                        source.setText("");
                    }*/

                    event.consume();
                }
            });

            trackBeats.getChildren().add((Node) rect);
        }
    }

    public void updateBeats(double songLength, int bpm) {
        int oldNumberOfBeats = trackBeats.getChildren().size();
        double numberOfBeats = Math.abs(((songLength/1000)/60 * bpm) - oldNumberOfBeats);
        String color = null;

        for(int i = 0; i< numberOfBeats; i++) {
            if(i % 4 == 0) {
                color = DARK_BLUE;
            } else color = HELL_BLUE;
            Rectangle rect = RectangleBuilder.create()
                    .arcHeight(0).arcWidth(0)
                    .width(8).height(55)
                    .fill(Color.web(color))
                    .strokeWidth(1).stroke(Color.BLACK).strokeType(StrokeType.INSIDE)
                    .id(String.valueOf(i+oldNumberOfBeats))
                    .build();
            trackBeats.getChildren().add((Node) rect);
        }
    }

    public boolean isActivated() {
        return trackActivated.isSelected();
    }

    public void setInstrument() {
        //setinstrument
    }

    //get instrument

    private void setImage(Instrument instrument) {

        switch(instrument) {
            case BASSGUITAR:
                instrumentImage = new Image("com/icons/PNG/16px/guitar_48.png", 32, 32, false, false);
                break;
            case DRUMS:
                instrumentImage = new Image("com/icons/PNG/16px/drums_48.png", 32, 32, false, false);
                break;
            case ELECTRICGUITAR:
                instrumentImage = new Image("com/icons/PNG/16px/guitar_48.png", 32, 32, false, false);
                break;
            case GUITAR:
                instrumentImage = new Image("com/icons/PNG/16px/guitar_48.png", 32, 32, false, false);
                break;
            case KEYBOARD:
                instrumentImage = new Image("com/icons/PNG/16px/keyboard_48.png", 32, 32, false, false);
                break;
            case PIANO:
                instrumentImage = new Image("com/icons/PNG/16px/keyboard_48.png", 32, 32, false, false);
                break;
        }
    }

}
