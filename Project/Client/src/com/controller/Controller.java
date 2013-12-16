package com.controller;

import com.musicbox.util.Instrument;
import com.util.InputDevice;
import com.util.InputLoader;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.sound.sampled.Mixer;
import java.io.File;
import java.util.ArrayList;

public class Controller {

    @FXML private Button btnCutTracksArea;
    @FXML private TextField textFieldSearch;
    @FXML private VBox composeAreaVBox;
    @FXML private Button btnNewTrack;
    @FXML private AnchorPane timelineAnchorPane;
    @FXML private Menu menuInputSetup;
    @FXML private FlowPane flowPaneTracksArea;
    @FXML private Button btnRecordTracksArea;
    @FXML private ImageView imageViewRecordTracksArea;
    @FXML private ImageView imageViewRecordComposeArea;
    @FXML private ImageView imageViewStop;
    @FXML private AnchorPane anchorPaneWindow;


    private Transition timelineTransition;
    private Rectangle timeline;
    private InputLoader inputLoader;
    private ToggleGroup toggleGroup;
    private Mixer mixer;
    private boolean inputSelected = false;
    private boolean recording = false;

    private int bpm;
    private int songLength;

    final Text dragText = new Text(500, 100, "drag me");

    /**
     * This method will be called when the FXML is loaded and is the initial configuration of the controller.
     */
    @FXML
    void initialize() {

        bpm = 120;
        timeline = RectangleBuilder.create()
                                            .x(117)
                                            .height(composeAreaVBox.getHeight())
                                            .width(1)
                                            .fill(Color.YELLOW)
                                            .arcHeight(0).arcWidth(0)
                                            .build();
        timeline.heightProperty().bind((ObservableValue<? extends Number>) composeAreaVBox.heightProperty());
        timelineAnchorPane.getChildren().add((Node) timeline);
        createTimelineTransition();
        inputLoader = new InputLoader();
        loadInputs();


        dragText.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Drag Entered");
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
                Dragboard db = dragText.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(dragText.getText());
                db.setContent(content);

                event.consume();
            }
        });
        flowPaneTracksArea.getChildren().add((Node) dragText);

        //flowPaneTracksArea.prefWidthProperty().bind((anchorPaneWindow.prefWidthProperty()));

        /*Setup Drag and Drop on the Tracks Area for importing external audio files*/
        setupDragAndDropTracksArea();
    }

    public VBox getComposeAreaVBox() {
        return composeAreaVBox;
    }


    public void handleRecComposeArea(ActionEvent actionEvent) {
        if(inputSelected == true) {
            recording = true;
            inputLoader.record();
        } else {
            System.out.println("Choose an input");
        }
    }

    public void newTrack(ActionEvent actionEvent) {
        TrackComponent track = new TrackComponent("G1", Instrument.GUITAR, 60000L, bpm);
        composeAreaVBox.getChildren().add((Node) track);
    }

    public void handlePlayComposeArea(ActionEvent actionEvent) {
        timelineTransition.play();
    }

    /**
     * Handle the action, which is fired, when the user the stop button of the compose area presses.
     * The timeline and the playback of the composition will be stopped.
     * @param actionEvent
     */
    public void handleStopComposeArea(ActionEvent actionEvent) {
        if(recording == true) {
            float length = inputLoader.stop();
            addNewMusicSegment("New Music Segment", length);
            recording = false;
        } else System.out.println("Nothing to stop");
        timelineTransition.stop();

    }

    /**
     * Creates a new transition for the timeline.
     */
    private void createTimelineTransition() {
        timelineTransition = TranslateTransitionBuilder.create()
                .duration(new Duration(3000))
                .node(timeline)
                .fromX(timeline.getX())
                .toX(1000)
                .interpolator(Interpolator.LINEAR)
                .build();

    }

    /**
     * This method will load all input and output devices and show them as a item list.
     */
    public void loadInputs() {
        toggleGroup = new ToggleGroup();
        ArrayList<InputDevice> inputs = inputLoader.loadInputs();
        for(int i = 0; i<inputs.size(); i++) {
            final RadioMenuItem radioMenuItem = new RadioMenuItem(inputs.get(i).getName());
            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItem.setUserData(inputs.get(i).getPortIndex());
            radioMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(radioMenuItem.getUserData());
                    inputLoader.setupInput((Integer) radioMenuItem.getUserData());
                    inputSelected = true;
                }
            });
            menuInputSetup.getItems().add(radioMenuItem);
        }
    }

    /**
     * Exit program.
     * @param actionEvent
     */
    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }


    /*public void updateTrackLength(ActionEvent actionEvent) {

        for (int i = 0; i<composeAreaVBox.getChildren().size(); i++) {
                Node trackComponent = (TrackComponent) composeAreaVBox.getChildren().get(i);
            if ( trackComponent instanceof TrackComponent) {
                //((TrackComponent) trackComponent).updateBeats();

            }

        }
    }    */

    public void addNewMusicSegment(String name, float length) {
        double musicSegmentWidth = convertLengthToWidth(length);
        MusicSegmentComponent musicSegment = new MusicSegmentComponent(name, length, musicSegmentWidth);
        flowPaneTracksArea.getChildren().add((Node) musicSegment);
    }

    public double convertLengthToWidth(double length) {
        return ((length/1000)/60)*bpm*15;
    }


    public void handleRecordTracksArea(ActionEvent actionEvent) {
        if(recording == true) {
            float length = inputLoader.stop();
            timelineTransition.stop();
            addNewMusicSegment("New Music Segment", length);
            recording = false;
            imageViewRecordTracksArea.setImage(imageViewRecordComposeArea.getImage());
        } else if(inputSelected == true) {
                recording = true;
                inputLoader.record();
                imageViewRecordTracksArea.setImage(imageViewStop.getImage());
            } else {
                System.out.println("Choose an input");
        }
    }

    public void handleCutTracksArea(ActionEvent actionEvent) {
    }

    public void handleConfig(ActionEvent actionEvent) {
    }

    public void handleChat(ActionEvent actionEvent) {

    }

    public void showControlls(ActionEvent actionEvent) {

    }

    private void setupDragAndDropTracksArea() {
        flowPaneTracksArea.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if(db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                    ;
                } else {
                    event.consume();
                }
            }
        });

        flowPaneTracksArea.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if(db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    String fileFormat = null;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        //currentIndex += 1;
                        //Song hinzufügen zur Liste
                        String fileName = file.getName();

                        fileFormat = filePath.substring(filePath.length()-3,filePath.length());
                        System.out.println("File Format: " + fileFormat);

                    }
                    if(!fileFormat.equals("wav")) {
                        System.out.println("File format not valid. Just .wav files");
                    } else {
                        //set new song to the view
                    }
                }
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }
}
