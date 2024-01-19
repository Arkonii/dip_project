package com.example

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class Main extends Application {
    @Override
    void start(Stage primaryStage) {
        primaryStage.title = "GroovyFX Example"

        def button = new Button("Click me!")
        button.onAction = { event ->
            println("Button clicked!")
        }

        def root = new StackPane()
        root.children << button

        primaryStage.scene = new Scene(root, 300, 250)

        primaryStage.show()
    }

    static void main(String[] args) {
        // Wskazanie ścieżki do bibliotek JavaFX
        System.setProperty("javafx.sdk.path", "C:\\Program Files\\Eclipse Adoptium\\javafx-sdk-20.0.2")

        // Uruchomienie aplikacji JavaFX
        launch(args)
    }
}