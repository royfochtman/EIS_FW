package com;

/**
 * Created by Roy on 14.12.13.
 */
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class TestDragAndDrop extends Application {
    ImageView dragImageView;
    @Override public void start(Stage stage) {
        stage.setTitle("Hello Drag And Drop");

        final Group root = new Group();
        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.LIGHTGREEN);

        final Text source = new Text(50, 100, "DRAG ME");
        source.setScaleX(2.0);
        source.setScaleY(2.0);

        final Text target = new Text(250, 100, "DROP HERE");
        target.setScaleX(2.0);
        target.setScaleY(2.0);

        Image image = new Image(getClass().getResourceAsStream("/com/icons/PNG/16px/android.png"));
        dragImageView = new ImageView(image);
        dragImageView.setFitHeight(30);
        dragImageView.setFitWidth(30);

        source.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                Group group = (Group) source.getScene().getRoot();
                //AnchorPane anchorPane = (AnchorPane) source.getScene().getRoot();

                if (!group.getChildren().contains(dragImageView)) {
                    group.getChildren().add(dragImageView);
                }

                dragImageView.setOpacity(0.5);
                dragImageView.toFront();
                dragImageView.setMouseTransparent(true);
                dragImageView.setVisible(true);
                /*dragImageView.relocate(
                        (int) (event.getSceneX() - dragImageView.getBoundsInLocal().getWidth() / 2),
                        (int) (event.getSceneY() - dragImageView.getBoundsInLocal().getHeight() / 2));*/
                dragImageView.relocate(event.getSceneX(), event.getSceneY());


                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        });


        source.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dragImageView.relocate(mouseEvent.getSceneX() - dragImageView.getX(), mouseEvent.getSceneY() - dragImageView.getY());
            }
        });

        target.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    target.setFill(Color.GREEN);
                }

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                target.setFill(Color.BLACK);

                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    target.setText(db.getString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source.setText("");
                }

                event.consume();
            }
        });

        root.getChildren().add(source);
        root.getChildren().add(target);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}