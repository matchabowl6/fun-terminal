package com.matchabowl6.funterminal;

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    private Pane mainPane = new Pane();
    private Pane outputPane = new Pane();
    private TextField commandTextField = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

    }
}
