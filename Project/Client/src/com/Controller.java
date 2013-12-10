package com;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.PGNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML private VBox composeAreaVBox;
    @FXML private Button btnNewTrack;

    @FXML
    void initialize() {

    }

    public VBox getComposeAreaVBox() {
        return composeAreaVBox;
    }


    public void recComposeArea(ActionEvent actionEvent) {
    }

    public void newTrack(ActionEvent actionEvent) {
        TrackComponent track = new TrackComponent(null, null, 60000, 130);
        composeAreaVBox.getChildren().add( (Node) track);
    }

    /*public void updateTrackLenght(ActionEvent actionEvent) {

        for (int i = 0; i<composeAreaVBox.getChildren().size(); i++) {
                Node trackComponent = (TrackComponent) composeAreaVBox.getChildren().get(i);
            if ( trackComponent instanceof TrackComponent) {
                //((TrackComponent) trackComponent).updateBeats();

            }

        }
    }    */


}
