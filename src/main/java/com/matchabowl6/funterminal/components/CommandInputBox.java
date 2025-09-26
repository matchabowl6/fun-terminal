package com.matchabowl6.funterminal.components;

import javafx.scene.control.TextField;

/**
 * Allows the user to input commands Bash style.
 */
public class CommandInputBox extends TextField {
    private String prefix = "user@device-name";
    private String workingDir = "~";

    public CommandInputBox() {
        getStylesheets().add("res/main.css");
        getStyleClass().add("command-input-box");
    }

    public void setText() {
        super.setText(prefix + ":" + workingDir + "$");
    }
}
