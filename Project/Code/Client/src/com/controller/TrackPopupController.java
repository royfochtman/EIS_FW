package com.controller;

import com.musicbox.util.Instrument;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.collections.FXCollections.*;

/**
 * Created by Roy on 21.12.13.
 */
public class TrackPopupController {

    @FXML private TextField textFieldTrackName;
    @FXML private ChoiceBox choiceBoxInstrument;

    private Stage dialogStage;
    private boolean okClicked = false;

    /**
     * Returns tracks name.
     * @return
     */
    public String getTrackName() {
        return textFieldTrackName.getText();
    }

    /**
     * Returns instrument.
     * @return
     */
    public Instrument getInstrument() {

        return Instrument.valueOf(choiceBoxInstrument.getSelectionModel().getSelectedItem().toString().replace(" ", "").toUpperCase());
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        ObservableList<Object> instrumentsList = observableArrayList();
        ArrayList<String> list = Instrument.getInstruments();
        for(int i=0; i < list.size(); i++) {
            instrumentsList.add(list.get(i).toString());
        }
        choiceBoxInstrument.setItems(instrumentsList);

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
        return ((textFieldTrackName.getText() != null) && (choiceBoxInstrument.getSelectionModel().getSelectedItem() != null));
    }

}
