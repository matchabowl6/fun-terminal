package com.matchabowl6.funterminal.commands;

import com.matchabowl6.funterminal.Command;
import com.matchabowl6.funterminal.OutputBuffer;

import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;

public class Time extends Command {
    public Time() {
        setName("time");
    }
    
    @Override
    public void execute(OutputBuffer buffer, String[] args) {
        ZonedDateTime dateTime = ZonedDateTime.now();
        buffer.push("The time is " + dateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")));
    }
}
