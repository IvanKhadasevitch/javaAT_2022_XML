package by.khadasevich.xml.service.builder;

import by.khadasevich.xml.bean.Article;
import by.khadasevich.xml.bean.Contacts;
import by.khadasevich.xml.bean.Journal;
import by.khadasevich.xml.service.parser.ParserException;
import by.khadasevich.xml.service.parser.imp.StaxParserImpl;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashSet;


public class JournalStAXBuilder {
    /**
     * Tag name <journal>.
     */
    private static final String JOURNAL_TAG = "journal";
    /**
     * Journal <title> tag.
     */
    private static final String JOURNAL_TITLE = "title";
    /**
     * Journal <contacts> tag.
     */
    private static final String JOURNAL_CONTACTS = "contacts";
    /**
     * Journal contacts address tag.
     */
    private static final String CONTACTS_ADDRESS_TAG = "address";
    /**
     * Journal contacts email tag.
     */
    private static final String CONTACTS_EMAIL_TAG = "email";
    /**
     * Journal contacts tel tag.
     */
    private static final String CONTACTS_TEL_TAG = "tel";
    /**
     * Journal contacts url tag.
     */
    private static final String CONTACTS_URL_TAG = "url";
    /**
     * Journal "articles" tag name.
     */
    private static final String JOURNAL_ARTICLES_TAG = "articles";
    /**
     * Articles "article" tag name.
     */
    private static final String ARTICLE_TAG = "article";
    /**
     * ID attribute name of <article> tag.
     */
    private static final String ARTICLE_ID = "ID";
    /**
     * Article <title> tag name.
     */
    private static final String ARTICLE_TITLE_TAG = "title";
    /**
     * Article <author> tag name.
     */
    private static final String ARTICLE_AUTHOR_TAG = "author";
    /**
     * Article <url> tag name.
     */
    private static final String ARTICLE_URL_TAG = "url";
    /**
     * Article <hotkeys> tag name.
     */
    private static final String ARTICLE_HOTKEYS_TAG = "hotkeys";
    /**
     * Article hotkeys <hotkey> tag name.
     */
    private static final String ARTICLE_HOTKEYS_HOTKEY_TAG = "hotkey";
    /**
     * Message for exception.
     */
    private static final String UNKNOWN_ELEMENT_IN_TAG =
            "Unknown element in tag <%s>";
    /**
     * Message for exception.
     */
    private static final String STAX_PARSING_ERROR = "StAX parsing error!";
    /**
     * StAX parser instance.
     */
    private final StaxParserImpl docParser;
    /**
     * Journal instance to fill xml data after parse.
     */
    private Journal journal;
    /**
     * Constructor JournalStAXBuilder.
     */
    public JournalStAXBuilder() {
        this.docParser = new StaxParserImpl();
    }

    /**
     * Build Journal instance by parsed xml data.
     * @param fileNameXML - xml data file name
     * @param fileNameXSD - xsd schema xml data file name
     * @return - filled with data Journal instance
     * @throws ParserException - if some errors occurred while parse xml file
     */
    public Journal buildJournal(final String fileNameXML,
                                final String fileNameXSD)
            throws ParserException {
        // set path to xml and schema sxd files
        docParser.setPath(fileNameXML, fileNameXSD);
        XMLStreamReader reader = null;
        String tagName;
        this.journal = new Journal();
        try {
            // take XMLStreamReader for xml data file
            reader = docParser.parse();
            // StAX parsing
            while (reader.hasNext()) {
                int type = reader.next();
                if (XMLStreamConstants.START_ELEMENT == type) {
                    tagName = reader.getLocalName();
                    switch (tagName) {
                        case JOURNAL_TITLE ->
                                journal.setJournalTitle(getXMLText(reader));
                        case JOURNAL_CONTACTS ->
                                buildJournalContacts(reader);
                        case JOURNAL_ARTICLES_TAG ->
                                journal.setArticles(new HashSet<>());
                        case ARTICLE_TAG ->
                                buildJournalArticle(reader);
                    }
                } else if (XMLStreamConstants.END_ELEMENT == type) {
                    tagName = reader.getLocalName();
                    if (JOURNAL_TAG.equals(tagName)) {
                        return journal;
                    }
                }
            }
        } catch (XMLStreamException ex) {
            String errorMessage = STAX_PARSING_ERROR + ex.getMessage();
            System.err.println(errorMessage);
            throw new ParserException(errorMessage, ex);
        }
        throw new ParserException(String.format(UNKNOWN_ELEMENT_IN_TAG,
                JOURNAL_TAG));
    }

    /**
     * Build Journal Contacts instance while xml parsing.
     * Set it to Journal corresponding field.
     * @param reader is XMLStreamReader
     * @throws ParserException
     */
    private void buildJournalContacts(final XMLStreamReader reader)
            throws ParserException {
        String tagName;
        Contacts contacts = new Contacts();
        try {
            while (reader.hasNext()) {
                int type = reader.next();
                if (XMLStreamConstants.START_ELEMENT == type) {
                    tagName = reader.getLocalName();
                    switch (tagName) {
                        case CONTACTS_ADDRESS_TAG ->
                                contacts.setAddress(getXMLText(reader));
                        case CONTACTS_TEL_TAG ->
                                contacts.setTel(getXMLText(reader));
                        case CONTACTS_EMAIL_TAG ->
                                contacts.setEmail(getXMLText(reader));
                        case CONTACTS_URL_TAG ->
                                contacts.setUrl(getXMLText(reader));
                    }
                } else if (XMLStreamConstants.END_ELEMENT == type) {
                    tagName = reader.getLocalName();
                    if (JOURNAL_CONTACTS.equals(tagName)) {
                        this.journal.setContacts(contacts);
                        return;
                    }
                }
            }
        } catch (XMLStreamException ex) {
            String errorMessage = STAX_PARSING_ERROR + ex.getMessage();
            System.err.println(errorMessage);
            throw new ParserException(errorMessage, ex);
        }
        throw new ParserException(String.format(UNKNOWN_ELEMENT_IN_TAG,
                JOURNAL_CONTACTS));
    }

    /**
     * Builds Journal's article instance while xml parsing.
     * Add it to Journal's articles Set.
     * @param reader is XMLStreamReader
     * @throws ParserException some error occurred
     * while xml parsing or building Article
     */
    private void buildJournalArticle(final XMLStreamReader reader)
            throws ParserException {
        Article article = new Article();
        // get <article> attributes
        try {
            int articleId = Integer.parseInt(reader.getAttributeValue(null,
                    ARTICLE_ID));
            article.setId(articleId);
        } catch (NumberFormatException exp) {
            throw new ParserException("Not integer article id format");
        }
        try {
            String tagName;
            while (reader.hasNext()) {
                int type = reader.next();
                if (XMLStreamConstants.START_ELEMENT == type) {
                    tagName = reader.getLocalName();
                    switch (tagName) {
                        case ARTICLE_TITLE_TAG ->
                                article.setArticleTitle(getXMLText(reader));
                        case ARTICLE_AUTHOR_TAG ->
                                article.setAuthor(getXMLText(reader));
                        case ARTICLE_URL_TAG ->
                                article.setArticleUrl(getXMLText(reader));
                        case ARTICLE_HOTKEYS_TAG ->
                                article.setHotKeys(new HashSet<>());
                        case ARTICLE_HOTKEYS_HOTKEY_TAG ->
                                article.getHotKeys().add(getXMLText(reader));
                    }
                } else if (XMLStreamConstants.END_ELEMENT == type) {
                    tagName = reader.getLocalName();
                    if (ARTICLE_TAG.equals(tagName)) {
                        journal.getArticles().add(article);
                        return;
                    }
                }
            }
        } catch (XMLStreamException ex) {
            String errorMessage = STAX_PARSING_ERROR + ex.getMessage();
            System.err.println(errorMessage);
            throw new ParserException(errorMessage, ex);
        }
        throw new ParserException(String.format(UNKNOWN_ELEMENT_IN_TAG,
                ARTICLE_TAG));
    }

    /**
     * Get text between tags.
     * @param reader is XMLStreamReader
     * @return text between tags
     * @throws XMLStreamException
     */
    private String getXMLText(final XMLStreamReader reader)
            throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
