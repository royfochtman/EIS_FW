package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
    @FXML private Button btn_record;
    @FXML private Button btn_play;
    @FXML private Button btn_stop;
    @FXML private Button btn_save;



    public void handleRecordButtonAction(ActionEvent actionEvent) {
    btn_save.setDisable(true);
    }

    public void handleSaveButtonAction(ActionEvent actionEvent) {
    }

    public void handlePlayButtonAction(ActionEvent actionEvent) {
    }

    public void handleStopButtonAction(ActionEvent actionEvent) {
    }
}
