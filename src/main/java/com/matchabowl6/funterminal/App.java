package com.matchabowl6.funterminal;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    private Pane mainPane = new Pane();
    private Pane outputPane = new Pane();
    private TextField commandTextField = new TextField();
    private DisplayedOutputBuffer outputBuffer = new DisplayedOutputBuffer(outputPane, Info.MAX_OUTPUT_LABELS);
    private OutputLabelManager labelManager = outputBuffer.getOutputLabelManager();
    private CommandHandler commandHandler = new CommandHandler(outputBuffer);

    private static void setRegionSize(Region r, double width, double height) {
        r.resize(width, height); // Necessary due to undesired sizing quirks without this method call
        r.setPrefWidth(width);
        r.setPrefHeight(height);
        r.setMinWidth(width);
        r.setMinHeight(height);
        r.setMaxWidth(width);
        r.setMaxHeight(height);
    }

    /**
     * Updates the size and position of the app's UI components based on the given
     * window width and height
     */
    private void updateSizeAndPosition(double width, double height) {
        setRegionSize(mainPane, width, height);

        double outputPaneWidth = width - 8.0;
		double outputPaneHeight = height - Info.OUTPUT_LABEL_HEIGHT * 2.2;
		setRegionSize(outputPane, outputPaneWidth, outputPaneHeight);
        outputPane.setTranslateX(8.0);

        setRegionSize(commandTextField, width, Info.OUTPUT_LABEL_HEIGHT);
        commandTextField.setTranslateX(0);
		commandTextField.setTranslateY(outputPaneHeight);

        labelManager.reposition();
    }

    private EventHandler<ActionEvent> mainCommandSubmitListener = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent _e) {
			String input = commandTextField.getText();
			if (input.isEmpty())
				return;

			commandTextField.setText("");
			commandHandler.parse(input);
		}
	};

    private void initialize(Stage stage) {
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
        //labelManager.reposition();

        labelManager.setTextColor(Color.rgb(125, 44, 255));
        labelManager.setFont(Info.PRIMARY_FONT);
        labelManager.push(Info.INITIAL_OUTPUT_TEXT);

        // Command text field
		commandTextField.setPromptText("Enter a command...");
		commandTextField.setStyle(
				"-fx-font-family: \"Roboto Mono\"; -fx-font-size: 16; -fx-text-fill: rgb(125, 44, 255); -fx-background-color: rgba(0, 0, 0, 0)");
        commandTextField.setOnAction(mainCommandSubmitListener);

        // Window resize response
        ChangeListener<Number> windowSizeListener = (_, _, _) -> {
            updateSizeAndPosition(stage.getWidth(), stage.getHeight());
        };
        stage.widthProperty().addListener(windowSizeListener);
        stage.heightProperty().addListener(windowSizeListener);

        mainPane.getChildren().add(commandTextField);
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
