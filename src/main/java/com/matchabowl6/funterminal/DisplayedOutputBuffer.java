package com.matchabowl6.funterminal;

import javafx.scene.layout.Pane;

public class DisplayedOutputBuffer implements OutputBuffer {
    private OutputLabelManager labelManager;

    public DisplayedOutputBuffer(Pane parentPane, int maxLabels) {
        labelManager = new OutputLabelManager(parentPane, maxLabels);
    }

    public OutputLabelManager getOutputLabelManager() {
        return labelManager;
    }

    public void push(String text) {
        labelManager.push(text);
    }
}
