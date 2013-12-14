package com;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by rfochtmann on 13.12.13.
 */
public class MusicSegmentComponent extends AnchorPane {

    @FXML Rectangle musicSegmentRectangle;
    @FXML Label musicSegmentName;
    @FXML HBox musicSegmentActions;


    public MusicSegmentComponent(String name, float length, double width) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/musicSegment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setId(name + "MusicSegmentComponent");
        if(name != null) {
            musicSegmentName.setText("(" + length + " ms)");
        }
        musicSegmentRectangle.setWidth(width);
        musicSegmentName.setMinWidth(width);
        musicSegmentName.setMaxWidth(width);
        this.setWidth(width);
        this.setMaxWidth(width);
        this.setMinWidth(width);

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                musicSegmentActions.setVisible(true);
                FadeTransition fadeTransition
                        = new FadeTransition(Duration.millis(300), musicSegmentActions);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent mouseEvent){
                FadeTransition fadeTransition
                        = new FadeTransition(Duration.millis(300), musicSegmentActions);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
            }
        });

        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Drag entered music segment");
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
                Dragboard db = musicSegmentName.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(musicSegmentName.getText());
                db.setContent(content);

                event.consume();
            }
        });

    }
}
