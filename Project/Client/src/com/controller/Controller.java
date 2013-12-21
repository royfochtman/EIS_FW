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

    @FXML public static AnchorPane anchorPaneClient;
    @FXML private Button btnCutTracksArea;
    @FXML private TextField textFieldSearch;
    @FXML private VBox composeAreaVBox;
    @FXML private Button btnNewTrack;
    @FXML private AnchorPane timelineAnchorPane;
    @FXML private Menu menuInputSetup;
    @FXML private Menu menuOutputSetup;
    @FXML private FlowPane flowPaneTracksArea;
    @FXML private Button btnRecordTracksArea;
    @FXML private ImageView imageViewRecordTracksArea;
    @FXML private ImageView imageViewRecordComposeArea;
    @FXML private ImageView imageViewStop;
    @FXML private AnchorPane anchorPaneWindow;
    @FXML private ChoiceBox choiceBoxInstrument;
    @FXML private ChoiceBox choiceBoxInputDevice;
    @FXML private TextField labelUserName;
    @FXML private GridPane paneNewSession;
    @FXML private GridPane paneLogin;
    @FXML private TextField textFieldRoomName;
    @FXML private TextField textFieldBPM;
    @FXML private TextField textFieldLength;
    @FXML private StackPane stackPaneLogin;
    @FXML private TextField textFieldTempo;


    private Transition timelineTransition;
    private Rectangle timeline;
    private InputLoader inputLoader;
    private ToggleGroup toggleGroup;
    private Mixer mixer;
    private boolean inputSelected = false;
    private boolean outputSelected = false;
    private boolean recording = false;
    private Duration timelineActualPosition;

    private String userName;
    private int bpm;
    private Long songLength;

    final Text dragText = new Text(500, 100, "drag me");

    private MusicRoomModel musicRoomModel;
    private WorkingAreaModel workingAreaModel;

    /**
     * This method will be called when the FXML is loaded and is the initial configuration of the controller.
     */
    @FXML
    void initialize() {
        paneNewSession.setVisible(false);
        paneLogin.setVisible(true);
        paneLogin.toFront();

        /**/
        inputLoader = new InputLoader();
        loadInputs();
        loadOutputs();

        ObservableList<Object> instrumentsList = observableArrayList();
        ArrayList<String> list = Instrument.getInstruments();
        for(int i=0; i < list.size(); i++) {
            instrumentsList.add(list.get(i).toString());
        }
        choiceBoxInstrument.setItems(instrumentsList);

        /**/
        textFieldTempo.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent t) {
                char ar[] = t.getCharacter().toCharArray();
                char ch = ar[t.getCharacter().toCharArray().length - 1];
                if (!(ch >= '0' && ch <= '9')) {
                    System.out.println("The char you entered is not a number");
                    t.consume();
                }
            }
        });

        textFieldTempo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if(workingAreaModel != null) {
                    workingAreaModel.setTempo(Integer.valueOf(s2));
                    //updateTrackLength();
                }
            }
        });
        /**/
        timeline = RectangleBuilder.create()
                                            .x(2)
                                            .height(composeAreaVBox.getHeight())
                                            .width(1)
                                            .fill(Color.YELLOW)
                                            .arcHeight(0).arcWidth(0)
                                            .build();
        timeline.heightProperty().bind((ObservableValue<? extends Number>) composeAreaVBox.heightProperty());

        timelineAnchorPane.getChildren().add((Node) timeline);
        //composeAreaVBox.getChildren().add( (Node) timeline);
        timelineAnchorPane.setLayoutX(100);
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

        timeline.toFront();
        createTimelineTransition();



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

            if(controller.isOkClicked()) {
                TrackController track = new TrackController(controller.getTrackName(), controller.getInstrument(), workingAreaModel.getLength(), workingAreaModel.getTempo());
                System.out.println("Length: " + workingAreaModel.getLength() + ". Tempo: " + workingAreaModel.getTempo());
                composeAreaVBox.getChildren().add((Node) track);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public void handlePlayComposeArea(ActionEvent actionEvent) {
        timelineTransition.play();
        //timelineTransition.playFrom(timelineActualPosition);
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
        //timelineActualPosition = timelineTransition.getCurrentTime();

    }

    /**
     * Creates a new transition for the timeline.
     */
    private void createTimelineTransition() {
        timelineTransition = TranslateTransitionBuilder.create()
                .duration(new Duration(3000))
                .node(timelineAnchorPane)
                .fromX(100)
                .toX(1000)
                .interpolator(Interpolator.LINEAR)
                .build();
    }

    /**
     * This method will load all input and output devices and show them as a item list.
     */
    public void loadInputs() {
        ObservableList<String> inputsObservableList = observableArrayList();
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

    public void loadOutputs() {
        toggleGroup = new ToggleGroup();
        ArrayList<InputDevice> outputs = inputLoader.loadOutputs();
        for(int i = 0; i<outputs.size(); i++) {
            final RadioMenuItem radioMenuItem = new RadioMenuItem(outputs.get(i).getName());
            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItem.setUserData(outputs.get(i).getPortIndex());
            radioMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(radioMenuItem.getUserData());
                    if(inputLoader.setupOutput((Integer) radioMenuItem.getUserData()) == true) {
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
     * Exit program.
     * @param actionEvent
     */
    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }


    public void updateTrackLength() {

        for (int i = 0; i<composeAreaVBox.getChildren().size(); i++) {
                Node trackController = (TrackController) composeAreaVBox.getChildren().get(i);
            if ( trackController instanceof TrackController) {
                ((TrackController) trackController ).updateBeats(workingAreaModel.getLength(), workingAreaModel.getTempo());
            }

        }
    }

    public void addNewMusicSegment(String name, float length) {
        double musicSegmentWidth = convertLengthToWidth(length);
        MusicSegmentModel musicSegmentModel = new MusicSegmentModel(1, name, Instrument.GUITAR, userName, InputLoader.lastPath, (long) length, musicRoomModel.getMusicRoom());
        MusicSegmentController musicSegment = new MusicSegmentController(name, length, musicSegmentWidth, InputLoader.lastPath);
        flowPaneTracksArea.getChildren().add((Node) musicSegment);
    }

    /**
     * Converts the length (ms) from the recorded audio data to pixel width,
     * which can be used to draw a rectangle as representation of this audio data.
     * @param length
     * @return
     */
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

    /**
     * Setups the drag and drop methods of the tracks area, so audio data
     * from type ".wav" can be imported and used.
     */
    private void setupDragAndDropTracksArea() {
        flowPaneTracksArea.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if(db.hasFiles()) {
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

    public void handleNewSession(ActionEvent actionEvent) {
        if((labelUserName.getText() != null)
                && (choiceBoxInputDevice.getSelectionModel().getSelectedItem() != null)
                && (choiceBoxInstrument.getSelectionModel().getSelectedItem() != null)) {

            paneNewSession.setVisible(true);
            paneLogin.setVisible(false);
            paneNewSession.toFront();
            //go on
        }else {
            System.out.println("Incomplete formular");

        }
    }

    public void handleLoadSessions(ActionEvent actionEvent) {

    }

    /**
     * Creates a new music room and working area (as models), with them the view
     * can interact.
     * @param actionEvent
     */
    public void handleCreateSession(ActionEvent actionEvent) {
        if((textFieldRoomName.getText() != null) && (textFieldBPM != null) && (textFieldLength.getText() != null)) {
            stackPaneLogin.toBack();
            stackPaneLogin.setVisible(false);

            textFieldTempo.setText(textFieldBPM.getText());
            bpm = Integer.valueOf(textFieldBPM.getText());
            songLength = Long.valueOf(textFieldLength.getText());
            musicRoomModel = new MusicRoomModel(textFieldRoomName.getText(), 1);

            workingAreaModel = new WorkingAreaModel(1, musicRoomModel.getMusicRoom(), textFieldRoomName.getText(), Integer.valueOf(textFieldTempo.getText()), userName, WorkingAreaType.PUBLIC, 1, Long.valueOf(textFieldLength.getText()));

            //textFieldTempo.textProperty().bindBidirectional(workingAreaModel.tempoProperty());
        }

        //WorkingArea workingArea = new WorkingArea(1, musicRoom, textFieldRoomName.getText(), Integer.valueOf(textFieldTempo.getText()), );

    }
}
