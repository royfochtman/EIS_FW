package com.controller;

import com.Main;
import com.model.MusicRoomModel;
import com.model.MusicSegmentModel;
import com.model.WorkingAreaModel;
import com.musicbox.util.Instrument;
import com.musicbox.util.WorkingAreaType;
import com.musicbox.util.database.entities.MusicRoom;
import com.musicbox.util.database.entities.WorkingArea;
import com.util.InputDevice;
import com.util.InputLoader;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import javax.sound.sampled.Mixer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.musicbox.util.Instrument.*;
import static javafx.collections.FXCollections.*;

/**
 * Main controller class for the interaction between views and models.
 */
public class Controller {

    @FXML
    public static AnchorPane anchorPaneClient;
    @FXML
    private Button btnCutTracksArea;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private VBox composeAreaVBox;
    @FXML
    private Button btnNewTrack;
    @FXML
    private AnchorPane timelineAnchorPane;
    @FXML
    private Menu menuInputSetup;
    @FXML
    private Menu menuOutputSetup;
    @FXML
    private static FlowPane flowPaneTracksArea;
    @FXML
    private Button btnRecordTracksArea;
    @FXML
    private ImageView imageViewRecordTracksArea;
    @FXML
    private ImageView imageViewRecordComposeArea;
    @FXML
    private ImageView imageViewStop;
    @FXML
    private AnchorPane anchorPaneWindow;
    @FXML
    private ChoiceBox choiceBoxInstrument;
    @FXML
    private ChoiceBox choiceBoxInputDevice;
    @FXML
    private TextField labelUserName;
    @FXML
    private GridPane paneNewSession;
    @FXML
    private GridPane paneLogin;
    @FXML
    private TextField textFieldRoomName;
    @FXML
    private TextField textFieldLength;
    @FXML
    private StackPane stackPaneLogin;
    @FXML
    private TextField textFieldTempo;
    @FXML
    private Tooltip tooltipRecComposeArea;
    @FXML
    private Button btnRecComposeArea;
    @FXML
    private StackPane stackPaneComposition;
    @FXML
    private AnchorPane anchorPaneComposition;
    @FXML
    private Slider sliderBPM;
    @FXML
    private Label labelBPM;

    private Transition timelineTransition;
    private Rectangle timeline;
    private InputLoader inputLoader;
    private ToggleGroup toggleGroup;
    private Mixer mixer;
    private boolean inputSelected = false;
    private boolean outputSelected = false;
    private boolean recording = false;
    private Duration timelineActualPosition = new Duration(0.0);

    public static String userName;
    private int bpm;
    private Long songLength;
    public static int musicSegmentIndex = 0;
    private int trackPixelWidth = 0;

    private MusicRoomModel musicRoomModel;
    private WorkingAreaModel workingAreaModel;

    /**
     * This method will be called when the FXML is loaded and is the initial configuration of the controller.
     */
    @FXML
    void initialize() {
        setupSliderBPM();

        stackPaneLogin.toFront();
        paneNewSession.setVisible(false);
        paneLogin.setVisible(true);
        paneLogin.toFront();

        textFieldLength.setText(String.valueOf(120000));
        textFieldLength.setDisable(true);

        loadInputs();
        loadOutputs();

        setupInstrumentsList();
        setupTempoListeners();

        createTimeline();
        setupDragAndDropTimeline();
        setupDragAndDropTimelineTarget();
        //createTimelineTransition();

        /*Setup Drag and Drop on the Tracks Area for importing external audio files*/
        setupDragAndDropTracksArea();
    }

    public VBox getComposeAreaVBox() {
        return composeAreaVBox;
    }

    public static FlowPane getTracksArea() {
        return flowPaneTracksArea;
    }

    public void setupSliderBPM() {
        sliderBPM.setMin(10);
        sliderBPM.setMax(200);
        sliderBPM.setValue(100);
        sliderBPM.setShowTickLabels(true);
        sliderBPM.setShowTickMarks(true);
        sliderBPM.setMajorTickUnit(10);
        sliderBPM.setMinorTickCount(10);
        sliderBPM.setBlockIncrement(10.0f);
        labelBPM.setText(String.format("%.0f", sliderBPM.getValue()));

        sliderBPM.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                labelBPM.setText(String.format("%.0f", number2));
            }
        });
    }
    /**
     * Records a new music segment.
     * @param actionEvent
     */
    public void handleRecComposeArea(ActionEvent actionEvent) {
        if (inputSelected == true) {
            recording = true;
            inputLoader.record();
        } else {
            /*Tooltip chooseInput = new Tooltip("Choose an input to start recording");
            btnRecComposeArea.setTooltip(chooseInput);
            chooseInput.show((Node) btnRecComposeArea, btnRecComposeArea.getLayoutX(), btnRecComposeArea.getLayoutY());*/

            //System.out.println("Choose an input");
            createWarningPopup("Input not selected", "Choose an input to start recording");
        }
    }

    /**
     * Creates a new track.
     * @param actionEvent
     */
    public void newTrack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/trackPopup.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Track");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            TrackPopupController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                TrackController track = new TrackController(controller.getTrackName(), controller.getInstrument(),
                        workingAreaModel.getLength(), workingAreaModel.getTempo());
                System.out.println("Length: " + workingAreaModel.getLength() + ". Tempo: " + workingAreaModel.getTempo());
                composeAreaVBox.getChildren().add((Node) track);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Start playing the main composition.
     * @param actionEvent
     */
    public void handlePlayComposeArea(ActionEvent actionEvent) {
        //timelineTransition.play();
        timelineTransition.playFrom(timelineActualPosition);
    }

    /**
     * Handle the action, which is fired, when the user the stop button of the compose area presses.
     * The timeline and the playback of the composition will be stopped.
     *
     * @param actionEvent
     */
    public void handleStopComposeArea(ActionEvent actionEvent) {
        if (recording == true) {
            float length = inputLoader.stop();
            addNewMusicSegment("New Music Segment", length);
            recording = false;
        } else System.out.println("Nothing to stop");

        timelineActualPosition = timelineTransition.getCurrentTime();
        timelineTransition.stop();
    }

    /**
     * Creates the timeline.
     */
    private void createTimeline() {
        timeline = RectangleBuilder.create()
                .x(2)
                .height(composeAreaVBox.getHeight())
                .width(1)
                .fill(Color.YELLOW)
                .arcHeight(0).arcWidth(0)
                .build();
        timeline.heightProperty().bind((ObservableValue<? extends Number>) composeAreaVBox.heightProperty());
        timelineAnchorPane.setLayoutX(100);
        timelineAnchorPane.setCursor(Cursor.W_RESIZE);
        timelineAnchorPane.getChildren().add(timeline);
    }

    /**
     * Setups the initial drag and drop events for the timeline.
     */
    private void setupDragAndDropTimeline() {

        timelineAnchorPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                timeline.setWidth(2);
            }
        });

        timelineAnchorPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                timeline.setWidth(1);
            }
        });

        timelineAnchorPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("on drag detected");
                Dragboard db = timelineAnchorPane.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("DND Timeline Anchor Pane");
                db.setContent(content);

                mouseEvent.consume();
            }
        });

        /*timelineAnchorPane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("Drag entered Timeline");

                Dragboard db = timelineAnchorPane.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                //content.put(null, timelineAnchorPane);
                content.putString("timeline");
                db.setContent(content);

                dragEvent.consume();
            }
        });  */

        timelineAnchorPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("on drag over");
                dragEvent.consume();
            }
        });
    }

    /**
     * Setups stackPaneComposition as target for the timelines drag and drop events.
     */
    private void setupDragAndDropTimelineTarget() {

        stackPaneComposition.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                /* data is dragged over the target */
                System.out.println("onDragOver Stackpane Composition");
                System.out.println("X: " + dragEvent.getX() + ". Y: " + dragEvent.getY());
                timelineAnchorPane.relocate(dragEvent.getX(), timelineAnchorPane.getLayoutY());

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (dragEvent.getGestureSource() != stackPaneComposition &&
                        dragEvent.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    dragEvent.acceptTransferModes(TransferMode.ANY);
                }

                dragEvent.consume();
            }
        });

        stackPaneComposition.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (dragEvent.getGestureSource() != stackPaneComposition &&
                        dragEvent.getDragboard().hasString()) {
                    //do something
                    timelineAnchorPane.relocate(dragEvent.getX(), timelineAnchorPane.getLayoutY());
                }

                dragEvent.consume();

            }
        });

        stackPaneComposition.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.consume();

            }
        });

        stackPaneComposition.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                /* data dropped */
                System.out.println("onDragDropped stackpane composition");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //target.setText(db.getString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                dragEvent.setDropCompleted(success);

                dragEvent.consume();
            }
        });

    }

    /**
     * Creates a new transition for the timeline.
     */
    private void createTimelineTransition() {
        timelineTransition = TranslateTransitionBuilder.create()
                .duration(new Duration(songLength))
                .node(timelineAnchorPane)
                .fromX(0)
                .toX(trackPixelWidth)
                .interpolator(Interpolator.LINEAR)
                .build();
    }

    /**
     * This method will load all input devices and show them as a item list.
     */
    public void loadInputs() {
        inputLoader = new InputLoader();
        ObservableList<String> inputsObservableList = observableArrayList();
        toggleGroup = new ToggleGroup();
        ArrayList<InputDevice> inputs = inputLoader.loadInputs();
        for (int i = 0; i < inputs.size(); i++) {
            final RadioMenuItem radioMenuItem = new RadioMenuItem(inputs.get(i).getName());

            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItem.setUserData(inputs.get(i).getPortIndex());
            radioMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //System.out.println(radioMenuItem.getUserData());
                    if (inputLoader.setupInput((Integer) radioMenuItem.getUserData()) == true) {
                        inputSelected = true;
                    } else {
                        inputSelected = false;
                    }
                }
            });

            inputsObservableList.add(inputs.get(i).getName());
            menuInputSetup.getItems().add(radioMenuItem);
            choiceBoxInputDevice.setItems(inputsObservableList);
        }
    }

    /**
     * Loads all output devices.
     */
    public void loadOutputs() {
        toggleGroup = new ToggleGroup();
        ArrayList<InputDevice> outputs = inputLoader.loadOutputs();
        for (int i = 0; i < outputs.size(); i++) {
            final RadioMenuItem radioMenuItem = new RadioMenuItem(outputs.get(i).getName());
            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItem.setUserData(outputs.get(i).getPortIndex());
            radioMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //System.out.println(radioMenuItem.getUserData());
                    if (inputLoader.setupOutput((Integer) radioMenuItem.getUserData()) == true) {
                        outputSelected = true;
                    } else {
                        outputSelected = false;
                    }
                }
            });
            menuOutputSetup.getItems().add(radioMenuItem);
        }
    }

    /**
     * Saves all instruments in the instrument choice box.
     */
    private void setupInstrumentsList() {
        ObservableList<Object> instrumentsList = observableArrayList();
        ArrayList<String> list = Instrument.getInstruments();
        for (int i = 0; i < list.size(); i++) {
            instrumentsList.add(list.get(i).toString());
        }
        choiceBoxInstrument.setItems(instrumentsList);
    }

    /**
     * Setup the Events and Properties from the Tempo field.
     */
    private void setupTempoListeners() {
        textFieldTempo.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent t) {
                char ar[] = t.getCharacter().toCharArray();
                char ch = ar[t.getCharacter().toCharArray().length - 1];
                if (!(ch >= '0' && ch <= '9')) {
                    System.out.println("The char you entered is not a number");
                    createWarningPopup("Just numbers are accepted", "The char you entered is not a number");
                    t.consume();
                }
            }
        });

        textFieldTempo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (workingAreaModel != null) {
                    workingAreaModel.setTempo(Integer.valueOf(s2));
                    //updateTrackLength();
                }
            }
        });
    }

    /**
     * Exit program.
     *
     * @param actionEvent
     */
    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Updates the number of beats when the songs beats or length was changed.
     */
    public void updateTrackLength() {

        for (int i = 0; i < composeAreaVBox.getChildren().size(); i++) {
            Node trackController = (TrackController) composeAreaVBox.getChildren().get(i);
            if (trackController instanceof TrackController) {
                ((TrackController) trackController).updateBeats(workingAreaModel.getLength(),
                        workingAreaModel.getTempo());
            }
        }
    }

    private void updateTrackPixelWidth() {
        trackPixelWidth = (int) (15 * ((songLength/60000) * bpm));
    }

    /**
     * Adds a new music segment to the main tracks area.
     * @param name
     * @param length
     */
    public void addNewMusicSegment(String name, float length) {
        double musicSegmentWidth = convertLengthToWidth(length);
        MusicSegmentModel musicSegmentModel = new MusicSegmentModel(musicSegmentIndex++, name, Instrument.GUITAR, userName,
                InputLoader.lastPath, (long) length, musicRoomModel.getMusicRoom());
        MusicSegmentController musicSegment = new MusicSegmentController(name, length, musicSegmentWidth,
                InputLoader.lastPath);
        flowPaneTracksArea.getChildren().add((Node) musicSegment);
    }

    /**
     * Converts the length (ms) from the recorded audio data to pixel width,
     * which can be used to draw a rectangle as representation of this audio data.
     *
     * @param length
     * @return
     */
    public double convertLengthToWidth(double length) {
        return ((length / 1000) / 60) * bpm * 15;
    }

    /**
     * Starts recording from the selected input. The recorded track will be shown in the main tracks area.
     * @param actionEvent
     */
    public void handleRecordTracksArea(ActionEvent actionEvent) {
        if (recording == true) {
            float length = inputLoader.stop();
            timelineTransition.stop();
            addNewMusicSegment("New Music Segment", length);
            recording = false;
            imageViewRecordTracksArea.setImage(imageViewRecordComposeArea.getImage());
        } else if (inputSelected == true) {
            recording = true;
            inputLoader.record();
            imageViewRecordTracksArea.setImage(imageViewStop.getImage());
        } else {
            //System.out.println("Choose an input");
            createWarningPopup("Input not selected", "Choose an input to start recording");

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

    /**
     * Setups the drag and drop methods of the tracks area, so audio data
     * from type ".wav" can be imported and used.
     */
    private void setupDragAndDropTracksArea() {
        flowPaneTracksArea.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
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
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    String fileFormat = null;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        //currentIndex += 1;
                        //Song hinzufügen zur Liste
                        String fileName = file.getName();

                        fileFormat = filePath.substring(filePath.length() - 3, filePath.length());
                        //System.out.println("File Format: " + fileFormat);

                    }
                    if (!fileFormat.equals("wav")) {
                        createWarningPopup("Invalid File format", "File format not valid. Just .wav files");
                    } else {
                        //set new song to the view
                    }
                }
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * @param actionEvent
     */
    public void handleNewSession(ActionEvent actionEvent) {
        if ((labelUserName.getText() != null)
                && (choiceBoxInputDevice.getSelectionModel().getSelectedItem() != null)
                && (choiceBoxInstrument.getSelectionModel().getSelectedItem() != null)) {

            paneNewSession.setVisible(true);
            paneLogin.setVisible(false);
            paneNewSession.toFront();
            //go on
        } else {
            createWarningPopup("Incomplete formular", "Fill all fields to continue");
        }
    }

    /**
     * Loads all sessions, which have been already saved in the DB.
     *
     * @param actionEvent
     */
    public void handleLoadSessions(ActionEvent actionEvent) {

    }

    /**
     * Creates a new music room and working area (as models), with them the view
     * can interact.
     *
     * @param actionEvent
     */
    public void handleCreateSession(ActionEvent actionEvent) {
        if ((!textFieldRoomName.getText().isEmpty()) && (!textFieldLength.getText().isEmpty())) {
            stackPaneLogin.toBack();
            stackPaneLogin.setVisible(false);
            textFieldTempo.setText(labelBPM.getText());
            bpm = Integer.valueOf(labelBPM.getText());
            songLength = Long.valueOf(textFieldLength.getText());
            musicRoomModel = new MusicRoomModel(textFieldRoomName.getText(), 1);

            workingAreaModel = new WorkingAreaModel(1, musicRoomModel.getMusicRoom(), textFieldRoomName.getText(),
                    Integer.valueOf(textFieldTempo.getText()), userName, WorkingAreaType.PUBLIC, 1,
                    Long.valueOf(textFieldLength.getText()));
            Main.primaryStage.setTitle(textFieldRoomName.getText());
            updateTrackPixelWidth();
            //textFieldTempo.textProperty().bindBidirectional(workingAreaModel.tempoProperty());
            createTimelineTransition();
        } else {
            createWarningPopup("Fill the formular", "Fill the formular to continue");
        }

        //WorkingArea workingArea = new WorkingArea(1, musicRoom, textFieldRoomName.getText(), Integer.valueOf(textFieldTempo.getText()), );

    }

    /**
     * Creates and shows a new warning popup window, which can be used to inform the user about the actual program
     * state.
     * @param popupTitle
     * @param warningText
     */
    public void createWarningPopup(String popupTitle, String warningText) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/warningPopup.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(popupTitle);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            WarningPopupController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setText(warningText);

            dialogStage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}