package com.matchabowl6.funterminal;

import javafx.scene.text.Font;

/**
 * Some information about the project.
 * 
 * @author Aaron Yu
 */
public interface Info {
	public final String NAME = "Fun Terminal";
	public final String VERSION = "0.5.0";
	public final String[] AUTHORS = { "Aaron Yu" };

	/**
	 * What's new in this version.
	 */
	public final String[] UPDATES = {
			"Added shorthand aliases to some commands",
			"Made music transitioning more smooth",
			"Converted format of song files to MP3",
			"Ported the app to JavaFX",
			"Implemented new appearance",
			"Made moving output labels less buggy",
			"Implemented the 'typewrite' command"
	};

	public final String INTRO_DISPLAY_NAME = "Fun Terminal";
	public final String INITIAL_OUTPUT_TEXT = "Enter a command, then press enter";
	public final Font PRIMARY_FONT = Font.font("Monospaced", 16);
	public final int MAX_OUTPUT_LABELS = 20;
	public final double OUTPUT_LABEL_HEIGHT = 35.0;
}
