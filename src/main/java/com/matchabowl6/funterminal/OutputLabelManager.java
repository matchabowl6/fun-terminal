package com.matchabowl6.funterminal;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Displays command output to the user.
 * 
 * @author Aaron Yu
 */
public class OutputLabelManager {
    private static final Duration TRANSITION_DURATION = Duration.seconds(0.3);

    private int maxLabels;
    private Color textColor = Color.BLACK;
    private Font font = null;
    private double yPosStart = 0;

    /**
     * Absolute value of the y-position difference between each subsequent output
     * label.
     */
    private double yPosDelta;

    /**
     * Most recent item is the head and the least recent item is the tail.
     * Queues implemented using linked lists can have the last element be either the
     * head or the tail.
     * However, for the purposes of OutputLabelManager's label positioning logic,
     * it's better for the most recent item to be at the head.
     */
    private LinkedList<Label> outputLabels = new LinkedList<Label>();

    private ArrayList<Transition> playingTransitions = new ArrayList<Transition>();

    private Pane pane;

    public OutputLabelManager(Pane parentPane, int maxLabels) {
        this.maxLabels = maxLabels;
        pane = parentPane;
        reposition();
    }

    public Color getTextColor(Color textColor) {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        for (Label l : outputLabels) {
            l.setTextFill(textColor);
        }
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
        for (Label l : outputLabels) {
            l.setFont(font);
        }
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    // movementMode 0 = no fade, movementMode 1 = fade in, movementMode 2 = fade out
    private Transition makeMoveAnim(Label label, int newLabelIndex, int movementMode) {
        double oldYPos = label.getTranslateY();
        Paint labelPaint = label.getTextFill();
        Color oldLabelColor = null;
        if (labelPaint instanceof Color color) {
            oldLabelColor = color;
        }
        double oldOpacity = oldLabelColor == null ? 0 : oldLabelColor.getOpacity();
        Transition tMove = new Transition() {
            {
                setCycleDuration(TRANSITION_DURATION);
            }

            protected void interpolate(double frac) {
                frac = 1.0 - (1.0 - frac) * (1.0 - frac); // Ease out quad

                label.setTranslateY(
                        lerp(oldYPos, yPosStart + yPosDelta * (maxLabels - 1 - newLabelIndex), frac));

                Paint oldPaint = label.getTextFill();
                Color oldColor = null;
                if (oldPaint instanceof Color cPaint) {
                    oldColor = cPaint;
                }

                if (movementMode == 2) {
                    label.setTextFill(new Color(
                            textColor.getRed(),
                            textColor.getGreen(),
                            textColor.getBlue(),
                            lerp(oldOpacity, 0.0, frac)));
                } else if (movementMode == 1 || (oldColor != null && oldColor.getOpacity() < 1.0)) {
                    label.setTextFill(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(),
                            lerp(oldOpacity, 1.0, frac)));
                }
            }
        };

        return tMove;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStylesheets().add("main.css");
        label.getStyleClass().add("output-line");
        label.setTextAlignment(TextAlignment.CENTER);

        return label;
    }

    /**
     * Displays a new output label
     * 
     * @param output The text to display
     */
    public Label push(String output) {
        for (int i = playingTransitions.size() - 1; i >= 0; i--) {
            Transition t = playingTransitions.remove(i);
            t.pause();
        }

        if (outputLabels.size() == maxLabels) {
            Label labelToRemove = outputLabels.pollLast();
            Transition tRemove = makeMoveAnim(labelToRemove, maxLabels, 2);
            tRemove.setOnFinished(new EventHandler<javafx.event.ActionEvent>() {
                public void handle(javafx.event.ActionEvent _e) {

                    pane.getChildren().remove(labelToRemove);
                }
            });
            tRemove.play();
        }

        Label newLabel = createLabel(output);
        newLabel.setTranslateY(yPosStart + yPosDelta * maxLabels);
        newLabel.setTextFill(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 0));
        newLabel.setFont(font);
        newLabel.setMinSize(pane.getWidth(), yPosDelta);
        outputLabels.add(0, newLabel);
        pane.getChildren().add(newLabel);

        int index = 0;
        for (Label l : outputLabels) {
            Transition t = makeMoveAnim(l, index, (index == 0) ? 1 : 0);
            playingTransitions.add(t);
            EventHandler<javafx.event.ActionEvent> onFinishHandler = new EventHandler<javafx.event.ActionEvent>() {
                public void handle(javafx.event.ActionEvent _e) {
                    playingTransitions.remove(t);
                }
            };
            t.setOnFinished(onFinishHandler);
            t.play();

            index++;
        }

        return newLabel;
    }

    public void clear() {
        // In the interest of thread safety, use a local variable to keep track
        // of the index number that we'll pass to makeMoveAnim().
        //
        // We start with outputLabels.size() instead of (outputLabels.size() - 1)
        // to make the output labels move up while fading out.
        int index = outputLabels.size();
        while (outputLabels.size() > 0) {
            Label labelToRemove = outputLabels.pollLast();

            Transition tRemove = makeMoveAnim(labelToRemove, index, 2);
            tRemove.setOnFinished(new EventHandler<javafx.event.ActionEvent>() {
                public void handle(javafx.event.ActionEvent _e) {
                    pane.getChildren().remove(labelToRemove);
                }
            });
            tRemove.play();
            index--;
        }
    }

    /**
     * Resizes and repositions the output labels so that they fit within the given
     * pane (which was specified in the constructor).
     */
    public void reposition() {
        yPosStart = pane.getTranslateY();
        yPosDelta = pane.getHeight() / maxLabels;
        int index = 0;
        for (Label label : outputLabels) {
            label.setTranslateY(yPosStart + yPosDelta * (maxLabels - 1 - index));
            label.setMinSize(pane.getWidth(), yPosDelta);
            index++;
        }
    }
}
