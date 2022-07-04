package by.khadasevich.xml.service.parser.imp;

import by.khadasevich.xml.service.parser.ParserException;
import by.khadasevich.xml.util.PathMaker;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 * Parse xml data file by DOM parser.
 */
public class DOMParserImpl {
    /**
     * Folder name in Resources where xml and xsd files are stored.
     */
    private static final String RESOURCES_DATA_FOLDER = "data";
    /**
     * Path to xml file.
     */
    private String pathXML;
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
     * Parse xml file to Document by DOM parser.
     * The Document interface represents the entire XML document.
     * Conceptually, it is the root of the document tree, and provides
     * the primary access to the document's data.
     *
     * @return instance of Document
     * @throws ParserException if some errors accused while parsing
     */
    public Document parse() throws ParserException {
        DocumentBuilder documentBuilder = null;
        try {
            DocumentBuilderFactory documentBuilderFactory =
                    DocumentBuilderFactory.newInstance();
            //plug xsd schema to be possible take attributes default values
            String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
            Schema schema = xsdFactory.newSchema(new File(this.pathXSD));
            // set builder parameters
            documentBuilderFactory.setExpandEntityReferences(false); /*
            prohibit the use of all protocols by external entities */
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setValidating(false); /* 'true' only
            to DTD validation */
            documentBuilderFactory.setSchema(schema); /* plug usd schema
            for validation */
            //finished xsd schema plug
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (SAXException e) {
            System.err.println("Failed to create schema " + e.getMessage());
            throw new ParserException(e.getMessage(), e);
        } catch (ParserConfigurationException e) {
            throw new ParserException(e.getMessage(), e);
        }
        // parse xml document
        Document document;
        try {
            document = documentBuilder.parse(new File(this.pathXML));
        } catch (SAXException | IOException e) {
            throw new ParserException(e.getMessage(), e);
        }
        document.getDocumentElement().normalize(); /* to ensure that the
         document hierarchy isn't affected by any extra white spaces
         or new lines within nodes. */
        return document;
    }
}
