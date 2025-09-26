package com.matchabowl6.funterminal.components;

import javafx.scene.layout.Region;

/**
 * Renders a terminal to a JavaFX application.
 */
public class TerminalRender extends Region {
    public TerminalRender() {
        getStylesheets().add("res/main.css");
        getStyleClass().add("terminal-bg");
        getChildren().add(new CommandInputBox());
    }
}
