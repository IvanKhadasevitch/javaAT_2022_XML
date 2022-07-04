package by.khadasevich.xml.controller.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Describe all possible function of program.
 * It Defines displayed name of command for user,
 * and it's realisation.
 */
@Getter
@AllArgsConstructor
public enum CommandName {
    /**
     * Parsing xml document with DOM parser.
     */
    DOM_PARSER("DOM parser", new DOMParserCommand()),
    /**
     * Parsing xml document with SAX parser.
     */
    SAX_PARSER("SAX parser", new SAXParserCommand()),
    /**
     * Parsing xml document with StAX parser.
     */
    STAX_PARSER("StAX parser", new StAXParserCommand()),
    /**
     * Exit of programme working.
     */
    EXIT("Exit", null);
    /**
     * Command title. It will be read by the user.
     */
    private final String title;
    /**
     * Implementation of Command.
     */
    private final Command command;
}
