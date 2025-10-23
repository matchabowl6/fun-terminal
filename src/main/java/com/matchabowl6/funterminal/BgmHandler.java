package com.matchabowl6.funterminal;

import javafx.animation.*;
import javafx.event.*;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Plays music.
 * 
 * @author Aaron Yu
 */
public class BgmHandler {
    private static final Duration FADE_DURATION = Duration.seconds(5.0);
    private MediaPlayer currentMediaPlayer;
    private String audioUri;

    private class BgmFadeOutFinishHandler implements EventHandler<ActionEvent> {
        public MediaPlayer mediaPlayer;

        public BgmFadeOutFinishHandler(MediaPlayer mPlayer) {
            mediaPlayer = mPlayer;
        }

        public void handle(ActionEvent event) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }

    public String getAudioUri() {
        return audioUri;
    }

    private void stopCurrentMediaPlayer() {
        MediaPlayer oldMediaPlayer = currentMediaPlayer;
        if (oldMediaPlayer != null) {
            currentMediaPlayer = null;
            Transition tFadeOut = new Transition() {
                {
                    setCycleDuration(FADE_DURATION);
                }

                protected void interpolate(double frac) {
                    oldMediaPlayer.setVolume(1.0 - frac);
                }
            };
            tFadeOut.setOnFinished(new BgmFadeOutFinishHandler(oldMediaPlayer));
            tFadeOut.play();
        }
    }

    public void stopBgm() {
        audioUri = null;
        stopCurrentMediaPlayer();
    }

    /**
     * Changes the music to the audio file pointed to by newUri.
     * 
     * @param newUri URI pointing to the audio file to play
     */
    public void setAudioUri(String newUri) throws Exception {
        audioUri = newUri;

        // Needs to be first so music doesn't change if we fail to load the audio file.
        Media audio = new Media(newUri);

        stopCurrentMediaPlayer();

        MediaPlayer newMediaPlayer = new MediaPlayer(audio);
        newMediaPlayer.setVolume(0.0);
        newMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        newMediaPlayer.play();
        Transition tFadeIn = new Transition() {
            {
                setCycleDuration(FADE_DURATION);
            }

            protected void interpolate(double frac) {
                newMediaPlayer.setVolume(frac);
            }
        };
        tFadeIn.play();
        currentMediaPlayer = newMediaPlayer;
    }
}
