package com.matchabowl6.funterminal;

import com.matchabowl6.funterminal.commands.*;

import java.util.ArrayList;

/**
 * Handles command input for Fun Terminal
 */
public class CommandHandler {
    private static final String ERR_MSG_UNRECOGNIZED_COMMAND = "Unrecognized command";

    /**
     * Commands specific to Fun Terminal (which should all be in the "commands"
     * sub-package)
     */
    private ArrayList<Command> appCommands = new ArrayList<Command>();

    private DisplayedOutputBuffer buffer;

    public CommandHandler(DisplayedOutputBuffer buffer) {
        this.buffer = buffer;
        appCommands.add(new Time());
    }

    /**
     * Parses the given user input string
     * 
     * @param input The user input string
     */
    public void parse(String input) {
        int firstWhitespaceIdx = input.indexOf(" ");
        String sCommand = input.substring(0, firstWhitespaceIdx == -1 ? input.length() : firstWhitespaceIdx);

        Command selectedCommand = null;
        for (Command c : appCommands) {
            boolean isMatch = sCommand.equals(c.getName());
            if (!isMatch) {
                for (String alias : c.getAliases()) {
                    if (sCommand.equals(alias)) {
                        isMatch = true;
                        break;
                    }
                }
            }

            if (isMatch) {
                selectedCommand = c;
                break;
            }
        }

        if (selectedCommand == null) {
            buffer.push(ERR_MSG_UNRECOGNIZED_COMMAND);
        } else {
            selectedCommand.execute(buffer,
                    (firstWhitespaceIdx == -1 || firstWhitespaceIdx == input.length() - 1) ? new String[0]
                            : input.substring(firstWhitespaceIdx + 1).split(" "));
        }
    }
}
