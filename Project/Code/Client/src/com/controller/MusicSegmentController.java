package com.controller;

import com.Main;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.InsetsBuilder;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayerBuilder;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by rfochtmann on 13.12.13.
 */
public class MusicSegmentController extends AnchorPane {

    @FXML
    Rectangle musicSegmentRectangle;
    @FXML
    Label musicSegmentName;
    @FXML
    HBox musicSegmentActions;
    @FXML
    Button btnPlayMusicSegment;
    @FXML
    Button btnStopMusicSegment;
    @FXML
    Button btnDeleteMusicSegment;
    /*@FXML
    MenuItem menuItemMoveSegmentTo;        */

    private MediaPlayer mediaPlayer;
    private Rectangle dragRectangle;
    private String audioPath;

    public MusicSegmentController(String name, float length, double width, String audioPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/view/musicSegment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.setId(Controller.userName + Controller.musicSegmentIndex);
        this.setPadding(InsetsBuilder.create().left(10).top(10).build());

        this.audioPath = audioPath;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //this.setId(name + "MusicSegmentComponent");
        if (name != null) {
            musicSegmentName.setText("(" + length + " ms)");
        }
        musicSegmentRectangle.widthProperty().bind(this.widthProperty());
        //musicSegmentRectangle.setWidth(width);
        musicSegmentName.setMinWidth(width);
        musicSegmentName.setMaxWidth(width);
        this.setWidth(width);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        dragRectangle = new Rectangle(musicSegmentRectangle.getWidth(), musicSegmentRectangle.getHeight(), musicSegmentRectangle.getFill());
        //this.setPadding(InsetsBuilder.create().left(10).right(10).build());

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                musicSegmentActions.setVisible(true);
                FadeTransition fadeTransition
                        = new FadeTransition(Duration.millis(200), musicSegmentActions);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent mouseEvent) {
                FadeTransition fadeTransition
                        = new FadeTransition(Duration.millis(200), musicSegmentActions);
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
                Dragboard db = musicSegmentName.startDragAndDrop(TransferMode.COPY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(musicSegmentName.getText());
                db.setContent(content);

                event.consume();
            }
        });

        Media media = new Media(new File(audioPath).toURI().toString());
        mediaPlayer = MediaPlayerBuilder.create().media(media).build();

        btnPlayMusicSegment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Play");
                mediaPlayer.play();
            }
        });

        btnStopMusicSegment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Stop");
                mediaPlayer.pause();
            }
        });

        btnDeleteMusicSegment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Delete");
                if (Controller.getTracksArea().getChildren().remove(getController())) {
                    System.out.println("Music segment deleted");
                } else {
                    System.out.println("music segment could not be deleted");
                }
            }
        });
    }

    public AnchorPane getController() {
        return this;
    }

    public void handleMoveSegmentTo(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/moveSegmentPopup.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Move selected segment to track");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            MoveSegmentPopupController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if(controller.isOkClicked()) {
                //update view in track
                int beat = controller.getBeat();
                String track = controller.getTrack();
                System.out.println("Segment will be moved.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
