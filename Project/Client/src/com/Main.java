package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/client_ui.fxml"));
        primaryStage.setTitle("Client UI");
        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.setMinWidth(1300);
        primaryStage.setMinHeight(600);
        primaryStage.centerOnScreen();
        primaryStage.show();

        this.primaryStage = primaryStage;

    }


    public static void main(String[] args) {
        launch(args);

    }
}
