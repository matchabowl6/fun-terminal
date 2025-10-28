package com.matchabowl6.funterminal;

import java.util.ArrayList;

public class Command {
    private String name = null;
    private ArrayList<String> aliases = new ArrayList<String>();

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getAliases() {
        return aliases;
    }

    protected void removeAlias(String alias) {
        aliases.remove(alias);
    }

    protected void addAlias(String alias) {
        aliases.add(alias);
    }

    /**
     * Executes this command.
     * @param args The list of command arguments. The first command argument should be the command name.
     */
    public void execute(OutputBuffer buffer, String[] args) {
    }
}
