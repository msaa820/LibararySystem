package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private BookController bookController;

    @Override
    public void start(Stage primaryStage) {
        // BookController to manage AVLTree and UI
        bookController = new BookController();

        // Build UI using BookController
        VBox rootLayout = bookController.buildUI();

        // Scene setup
        Scene scene = new Scene(rootLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // Link CSS file

        primaryStage.setTitle("Library System");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
