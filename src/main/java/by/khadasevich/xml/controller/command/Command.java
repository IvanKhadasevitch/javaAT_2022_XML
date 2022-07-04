package by.khadasevich.xml.controller.command;

import by.khadasevich.xml.service.parser.ParserException;

/**
 * Interface Command for realisation command template.
 * All program functions will be released as separate
 * implementation of Command interface
 */
public interface Command {
    /**
     * Execute command.
     */
    void execute() throws ParserException;
}
