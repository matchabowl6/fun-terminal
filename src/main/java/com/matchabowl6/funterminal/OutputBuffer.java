package com.matchabowl6.funterminal;

/**
 * Interface for classes that display output to the user.
 */
public interface OutputBuffer {
    public void clear();
    public void push(String text);
}
