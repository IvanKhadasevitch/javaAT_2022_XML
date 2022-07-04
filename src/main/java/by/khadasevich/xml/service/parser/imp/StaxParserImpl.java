package by.khadasevich.xml.service.parser.imp;

import by.khadasevich.xml.service.parser.ParserException;
import by.khadasevich.xml.util.PathMaker;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StaxParserImpl {
    /**
     * Folder name in Resources where xml and xsd files are stored.
     */
    private static final String RESOURCES_DATA_FOLDER = "data";
    /**
     * Path to xml file.
     */
    private String pathXML;
    /**
     * Set path to xml and xsd schema files.
     * @param fileNameXML - xml file name
     * @param fileNameXSD - corresponded to xml schema xsd file name.
     */
    public void setPath(final String fileNameXML, final String fileNameXSD) {
        this.pathXML = PathMaker.makeToResourcesSubFolderPath(fileNameXML,
                RESOURCES_DATA_FOLDER);
    }
    /**
     * Create XMLStreamReader for parse xml data file.
     * @return - new XMLStreamReader
     * @throws ParserException if XMLStreamReader creation error
     */
    public XMLStreamReader parse() throws ParserException {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        XMLInputFactory inputFactory = null;
        try {
            inputFactory = XMLInputFactory.newInstance();
            inputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
                    Boolean.FALSE); /* disable external entities declarations */
            inputStream = new FileInputStream(new File(this.pathXML));
            reader = inputFactory.createXMLStreamReader(inputStream);
        } catch (XMLStreamException e) {
            String errorMessage = "Stax reader creation error"
                    + e.getMessage();
            System.err.println(errorMessage);
            throw new ParserException(errorMessage, e);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            throw new ParserException(e.getMessage(), e);
        }
        return reader;
    }
}
