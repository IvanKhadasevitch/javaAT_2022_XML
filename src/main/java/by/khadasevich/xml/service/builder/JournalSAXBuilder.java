package by.khadasevich.xml.service.builder;

import by.khadasevich.xml.bean.Journal;
import by.khadasevich.xml.service.parser.ParserException;
import by.khadasevich.xml.service.parser.imp.SaxParserImpl;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;

public class JournalSAXBuilder {
    /**
     * SaxParserImpl instance to create SAX xml data file reader.
     */
    private final SaxParserImpl docParser;
    /**
     * JournalSAXBuilder constructor.
     */
    public JournalSAXBuilder() {
        this.docParser = new SaxParserImpl();
    }
    /**
     * Build Journal instance after parsing xml data file.
     * Fill Journal's fields with mxl data
     * @param fileNameXML - xml data file name
     * @param fileNameXSD - xsd schema file name for corresponding xml data file
     * @return journal filled with parsed data form xml data file
     * @throws ParserException when some error occurred while parsing
     */
    public Journal buildJournal(final String fileNameXML,
                                final String fileNameXSD)
            throws ParserException {
        // set path to xml and schema sxd files
        docParser.setPath(fileNameXML, fileNameXSD);
        // create parser handler
        JournalSAXHandler journalSAXHandler = new JournalSAXHandler();
        XMLReader reader;
        try {
            // get SAX reader
            reader = docParser.parse();
            reader.setContentHandler(journalSAXHandler);
            // parsing XML data file
            reader.parse(docParser.getPathXML());
        } catch (SAXException e) {
            String errorMessage = "SAX parser error: " + e.getMessage();
            System.err.println(errorMessage);
            throw new ParserException(errorMessage, e);
        } catch (IOException e) {
            String errorMessage = "I/О stream error: " + e.getMessage();
            System.err.println(errorMessage);
            throw new ParserException(e.getMessage(), e);
        }
        return journalSAXHandler.getJournal();
    }
}
