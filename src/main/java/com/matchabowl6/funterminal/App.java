package com.matchabowl6.funterminal;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    private Pane mainPane = new Pane();
    private Pane outputPane = new Pane();
    private TextField commandTextField = new TextField();
    private DisplayedOutputBuffer outputBuffer = new DisplayedOutputBuffer(mainPane, Info.MAX_OUTPUT_LABELS);
    private OutputLabelManager labelManager = outputBuffer.getOutputLabelManager();

    /**
     * Updates the size and position of the app's UI components based on the given
     * window width and height
     */
    private void updateSizeAndPosition(double width, double height) {

    }

    private void initialize(Stage stage) {
        // Window
        stage.setMinWidth(800.0);
        stage.setMinHeight(600.0);

        // Node size and position

        // Math.max is necessary here due to layout issues
        // setNodeSizeAndPosition(Math.max(stage.getMinWidth(), stage.getWidth()),
        // Math.max(stage.getMinHeight(), stage.getHeight()));
        updateSizeAndPosition(stage.getWidth(), stage.getHeight());

        // Background
        // jfxMainPane.setPrefSize(stage.getWidth(), stage.getHeight());
        mainPane.setBackground(Background.fill(Color.rgb(9, 0, 23)));
        mainPane.setId("terminal-bg");

        // Output pane
        mainPane.getChildren().add(outputPane);

        // Output Label Manager
        labelManager.reposition();

        labelManager.setTextColor(Color.rgb(125, 44, 255));
        labelManager.setFont(Info.PRIMARY_FONT);
        labelManager.push(Info.INITIAL_OUTPUT_TEXT);

        // Window resize response
        ChangeListener<Number> windowSizeListener = (_, _, _) -> {
            updateSizeAndPosition(stage.getWidth(), stage.getHeight());
            labelManager.reposition();
        };
        stage.widthProperty().addListener(windowSizeListener);
        stage.heightProperty().addListener(windowSizeListener);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setWidth(800.0);
        stage.setHeight(600.0);
        initialize(stage);

        stage.setTitle("Fun Terminal");
        stage.getIcons().add(new javafx.scene.image.Image("file:appicon.png"));
        stage.show();
    }
}
