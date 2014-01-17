package com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * Created by rfochtmann on 25.12.13.
 */
public class WarningPopupController {
    @FXML
    private Label labelWarning;
    @FXML
    private Button buttonOkWarningPopup;

    private Stage dialogStage;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Sets the warning text in the popups label.
     * @param text
     */
    public void setText(String text) {
        labelWarning.setText(text);
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
    private void handleOkWarningPopup() {
            dialogStage.close();
    }
}
