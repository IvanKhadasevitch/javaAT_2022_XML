package by.khadasevich.xml.controller.command;

import by.khadasevich.xml.bean.Journal;
import by.khadasevich.xml.service.builder.JournalSAXBuilder;
import by.khadasevich.xml.service.parser.ParserException;

public class SAXParserCommand implements Command {
    /**
     * XML file name with Journal data.
     */
    private static final String XML_FILE_NAME = "journal.xml";
    /**
     * XSD schema file name of XML file with Journal data.
     */
    private static final String XSD_FILE_NAME = "journal.xsd";

    /**
     *  Execute parsing xml document with SAX parser.
     * @throws ParserException when some parsing error is occurred
     */
    @Override
    public void execute() throws ParserException {
        JournalSAXBuilder saxBuilder = new JournalSAXBuilder();
        Journal journal = saxBuilder.buildJournal(XML_FILE_NAME,
                XSD_FILE_NAME);
        // present result of SAX parsing
        System.out.println(journal);
    }
}
