package com;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.PGNode;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;

public class Controller {

    @FXML private VBox composeAreaVBox;
    @FXML private Button btnNewTrack;

    @FXML
    void initialize() {
        Rectangle timeline = RectangleBuilder.create()
                                            .x(100)
                                            .height(composeAreaVBox.getHeight())
                                            .width(4)
                                            .fill(Color.YELLOW)
                                            .arcHeight(0).arcWidth(0)
                                            .build();
        timeline.heightProperty().bind((ObservableValue<? extends Number>) composeAreaVBox.heightProperty());
        composeAreaVBox.getChildren().add((Node) timeline);

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
