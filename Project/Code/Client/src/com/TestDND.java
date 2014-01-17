package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by rfochtmann on 30.12.13.
 */
public class TestDND extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/anchorPainTest.fxml"));
        //primaryStage.setTitle("Client UI");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.centerOnScreen();
        primaryStage.show();

        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);

    }
}
