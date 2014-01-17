package com.controller;

import com.Main;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
        return Integer.valueOf(textFieldBeat.getText());
    }

    public String getTrack() {
        return choiceBoxTrack.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    private void initialize() {
        textFieldBeat.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
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

        ObservableList<Node> tracksList = Controller.getComposeAreaVBox().getChildren();
        ObservableList<Object> observableTracksList = observableArrayList();
        if(!tracksList.isEmpty()) {
            for(int i = 0; i < tracksList.size(); i++) {
                TrackController track = (TrackController) tracksList.get(i);
                String name = track.getName().getValue();
                System.out.println(name);
                observableTracksList.add(name);
            }
            choiceBoxTrack.setItems(observableTracksList);
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
