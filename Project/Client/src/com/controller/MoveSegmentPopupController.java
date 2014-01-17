package com.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by rfochtmann on 17.01.14.
 */
public class MoveSegmentPopupController {
    @FXML
    private ChoiceBox choiceBoxTrack;
    @FXML
    private TextField textFieldBeat;

    private Stage dialogStage;
    private boolean okClicked = false;

    public int getBeat() {
        return Integer.getInteger(textFieldBeat.getText());
    }

    public String getTrack() {
        return new String();
    }

    @FXML
    private void initialize() {
        ObservableList<Node> tracksList = Controller.getComposeAreaVBox().getChildren();
        ObservableList<Object> observableTracksList = observableArrayList();
        if(!tracksList.isEmpty()) {
            for(int i = 0; i < tracksList.size(); i++) {
                TrackController track = (TrackController) tracksList.get(i);
                String name = track.getName().getValue();
                System.out.println(name);
                observableTracksList.add(name);
            }
            choiceBoxTrack.setItems(tracksList);
        }
    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        return ((textFieldBeat.getText() != null) && (choiceBoxTrack.getSelectionModel().getSelectedItem() != null));
    }

}
