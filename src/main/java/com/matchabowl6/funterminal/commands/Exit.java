package com.matchabowl6.funterminal.commands;

import com.matchabowl6.funterminal.Command;
import com.matchabowl6.funterminal.OutputBuffer;

/**
 * Fun Terminal's exit command
 */
public class Exit extends Command {
    public Exit() {
        setName("exit");
    }

    @Override
    public void execute(OutputBuffer _o, String[] _a) {
        System.exit(0);
    }
}
