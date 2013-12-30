package com.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Created by rfochtmann on 30.12.13.
 */
public class TestDNDController {

    @FXML
    AnchorPane anchorPaneDND;
    @FXML
    FlowPane flowPaneDND;
    @FXML
    Rectangle rectangleAnchorPane;
    @FXML
    Rectangle rectangleFlowPane;


    @FXML
    void initialize() {
        /*anchorPaneDND.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //timeline.setWidth(2);
                System.out.println("on Mouse entered");
            }
        });

        anchorPaneDND.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //timeline.setWidth(1);
                System.out.println("on Mouse Exited");
            }
        });      */

        anchorPaneDND.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("on drag detected");
                Dragboard db = anchorPaneDND.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("DND Rectangle Anchor Pane");
                db.setContent(content);

                mouseEvent.consume();
            }
        });

        /*rectangleAnchorPane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("Drag entered Timeline");

                Dragboard db = rectangleAnchorPane.startDragAndDrop(TransferMode.ANY);


                ClipboardContent content = new ClipboardContent();
                //content.put(null, timelineAnchorPane);
                content.putString("on drag entered");
                db.setContent(content);

                dragEvent.consume();
            }
        });*/

        anchorPaneDND.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("on drag over");
                dragEvent.consume();

            }
        });

        rectangleFlowPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("on drag over rectangle flow pane");
                dragEvent.acceptTransferModes(TransferMode.ANY);
                dragEvent.consume();
            }
        });

        rectangleFlowPane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                rectangleFlowPane.setFill(Color.YELLOW);
                dragEvent.consume();
            }
        });

        rectangleFlowPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                rectangleFlowPane.setFill(Color.AQUA);
                dragEvent.consume();
            }
        });

        rectangleFlowPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;

                if(db.hasString()) {
                    System.out.println("on drag dropped, success");
                    success = true;
                }

                dragEvent.setDropCompleted(success);
                dragEvent.consume();
            }
        });

        anchorPaneDND.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if(dragEvent.getTransferMode() == TransferMode.MOVE) {
                    System.out.println("Drag Done Rectangle Anchor Pane");
                }
                dragEvent.consume();
            }
        });
    }
}
