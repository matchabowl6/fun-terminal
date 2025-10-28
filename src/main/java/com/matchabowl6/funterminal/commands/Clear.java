package com.matchabowl6.funterminal.commands;

import com.matchabowl6.funterminal.Command;
import com.matchabowl6.funterminal.OutputBuffer;

/**
 * Command that clears OutputBuffers passed to the command's execute() method.
 */
public class Clear extends Command {
    public Clear() {
        setName("clear");
    }

    @Override
    public void execute(OutputBuffer buffer, String[] _a) {
        buffer.clear();
    }
}
