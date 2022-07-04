package by.khadasevich.xml.service.parser.imp;

import by.khadasevich.xml.service.parser.ParserException;
import by.khadasevich.xml.util.PathMaker;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class SaxParserImpl {
    /**
     * Folder name in Resources where xml and xsd files are stored.
     */
    private static final String RESOURCES_DATA_FOLDER = "data";
    /**
     * Path to xml file.
     */
    private String pathXML;
    /**
     * Get path to xml data file.
     * @return path to xml data file
     */
    public String getPathXML() {
        return pathXML;
    }
    /**
     *  Path to xsd schema file.
     */
    private String pathXSD;
    /**
     * Set path to xml and xsd schema files.
     * @param fileNameXML - xml file name
     * @param fileNameXSD - corresponded to xml schema xsd file name.
     */
    public void setPath(final String fileNameXML, final String fileNameXSD) {
        this.pathXML = PathMaker.makeToResourcesSubFolderPath(fileNameXML,
                RESOURCES_DATA_FOLDER);
        this.pathXSD = PathMaker.makeToResourcesSubFolderPath(fileNameXSD,
                RESOURCES_DATA_FOLDER);
    }

    /**
     * Create XMLReader for SAX parser to read xml data file.
     * @return XMLReader
     * @throws ParserException if XMLReader creation error
     */
    public XMLReader parse() throws ParserException {
        XMLReader xmlReader = null;
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            //plug xsd schema to be possible take attributes default values
            String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
            Schema schema = xsdFactory.newSchema(new File(this.pathXSD));
            // set factory parameters
            saxParserFactory.setNamespaceAware(true);
            saxParserFactory.setValidating(false); /* 'true' only
            to DTD validation */
            saxParserFactory.setSchema(schema); /* plug usd schema
            for validation and to be possible take attributes default values */
            //finished xsd schema plug
            SAXParser saxParser = saxParserFactory.newSAXParser();
            xmlReader = saxParser.getXMLReader();
        } catch (SAXException e) {
            System.err.println("Failed to create schema " + e.getMessage());
            throw new ParserException(e.getMessage(), e);
        } catch (ParserConfigurationException e) {
            throw new ParserException(e.getMessage(), e);
        }
        return xmlReader;
    }
}
