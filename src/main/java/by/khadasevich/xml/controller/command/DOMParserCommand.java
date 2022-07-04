package by.khadasevich.xml.controller.command;

import by.khadasevich.xml.bean.Journal;
import by.khadasevich.xml.service.builder.JournalDOMBuilder;
import by.khadasevich.xml.service.parser.ParserException;

public class DOMParserCommand implements Command {
    /**
     * XML file name with Journal data.
     */
    private static final String XML_FILE_NAME = "journal.xml";
    /**
     * XSD schema file name of XML file with Journal data.
     */
    private static final String XSD_FILE_NAME = "journal.xsd";
    /**
     * Execute parsing xml document with DOM parser.
     */
    @Override
    public void execute() throws ParserException {
        JournalDOMBuilder domBuilder = new JournalDOMBuilder();
        Journal journal = domBuilder.buildJournal(XML_FILE_NAME, XSD_FILE_NAME);
        // show result of parsing
        System.out.println(journal);
    }
}
