package com;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.PGNode;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.util.Duration;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML private Button btnCutTracksArea;
    @FXML private TextField textFieldSearch;
    @FXML private VBox composeAreaVBox;
    @FXML private Button btnNewTrack;
    @FXML private AnchorPane timelineAnchorPane;
    @FXML private Menu menuInputSetup;
    @FXML private FlowPane flowPaneTracksArea;


    private Transition timelineTransition;
    private Rectangle timeline;
    private InputLoader inputLoader;
    private ToggleGroup toggleGroup;
    private Mixer mixer;
    private boolean inputSelected = false;

    /**
     * This method will be called when the FXML is loaded and is the initial configuration of the controller.
     */
    @FXML
    void initialize() {
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

    }

    public VBox getComposeAreaVBox() {
        return composeAreaVBox;
    }


    public void handleRecComposeArea(ActionEvent actionEvent) {
        if(inputSelected == true) {
            inputLoader.record();
        } else {
            System.out.println("Choose an input");
        }
    }

    public void newTrack(ActionEvent actionEvent) {
        TrackComponent track = new TrackComponent("G1", null, 60000, 130);
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
       float length = inputLoader.stop();
       timelineTransition.stop();
       addNewMusicSegment("New Music Segment", length);
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
        List inputs = inputLoader.loadInputs();
        for(int i = 0; i<inputs.size(); i++) {
            final RadioMenuItem radioMenuItem = new RadioMenuItem(inputs.get(i).toString());
            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItem.setUserData(i);
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

        MusicSegmentComponent musicSegment = new MusicSegmentComponent(name, length);
        flowPaneTracksArea.getChildren().add((Node) musicSegment);


    }


}
