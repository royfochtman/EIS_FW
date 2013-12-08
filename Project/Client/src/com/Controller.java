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

    public VBox getComposeAreaVBox() {
        return composeAreaVBox;
    }


    public void recComposeArea(ActionEvent actionEvent) {
    }

    public void newTrack(ActionEvent actionEvent) {
        TrackComponent track = new TrackComponent();
        composeAreaVBox.getChildren().add( (Node) track);
    }

     @FXML
    void initialize() {

     }
}
