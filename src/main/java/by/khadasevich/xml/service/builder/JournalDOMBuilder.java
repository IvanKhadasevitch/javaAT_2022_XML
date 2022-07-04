package by.khadasevich.xml.service.builder;

import by.khadasevich.xml.bean.Article;
import by.khadasevich.xml.bean.Contacts;
import by.khadasevich.xml.bean.Journal;
import by.khadasevich.xml.service.parser.ParserException;
import by.khadasevich.xml.service.parser.imp.DOMParserImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashSet;
import java.util.Set;

public class JournalDOMBuilder {
    /**
     * Journal "title" tag name.
     */
    private static final String JOURNAL_TITLE_TAG = "title";
    /**
     * Journal "contacts" tag name.
     */
    private static final String JOURNAL_CONTACTS_TAG = "contacts";
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
     * Implementation DOM Parser.
     */
    private final DOMParserImpl docParser;

    /**
     * JournalDOMBuilder constructor.
     */
    public JournalDOMBuilder() {
        this.docParser = new DOMParserImpl();
    }

    /**
     * Build Journal instance by parsed xml data.
     * @param fileNameXML - xml data file name
     * @param fileNameXSD - xsd schema xml data file name
     * @return - filled with data Journal instance
     * @throws ParserException - if some errors accused while parse xml file
     */
    public Journal buildJournal(final String fileNameXML,
                                final String fileNameXSD)
            throws ParserException {
        // set path to xml and schema sxd files
        docParser.setPath(fileNameXML, fileNameXSD);
        // parsing XML-file
        Document doc = docParser.parse();
        Element root = doc.getDocumentElement();
        Journal journal = new Journal();
        // fill journal
        journal.setJournalTitle(getElementTextContent(root,
                JOURNAL_TITLE_TAG));
        // fill Contacts
        Contacts contacts = new Contacts();
        Element contactsElement = (Element) root
                .getElementsByTagName(JOURNAL_CONTACTS_TAG).item(0);
        contacts.setAddress(getElementTextContent(contactsElement,
                CONTACTS_ADDRESS_TAG));
        contacts.setTel(getElementTextContent(contactsElement,
                CONTACTS_TEL_TAG));
        contacts.setEmail(getElementTextContent(contactsElement,
                CONTACTS_EMAIL_TAG));
        contacts.setUrl(getElementTextContent(contactsElement,
                CONTACTS_URL_TAG));
        // set Contacts to Journal
        journal.setContacts(contacts);
        // fill Articles
        Set<Article> articlesSet = new HashSet<>();
        Element articlesTag =
                (Element) root.getElementsByTagName(JOURNAL_ARTICLES_TAG)
                        .item(0);
        // take children list of elements <articles>
        NodeList articleList = articlesTag.getElementsByTagName(ARTICLE_TAG);
        for (int i = 0; i < articleList.getLength(); i++) {
            Element articleElement = (Element) articleList.item(i);
            Article article = buildArticle(articleElement);
            articlesSet.add(article);
        }
        // set articles to Journal
        journal.setArticles(articlesSet);
        return journal;
    }

    /**
     * Build Article instance filled xml data.
     * @param articleElement - xml node with tag <article>
     * @return - Article instance filled xml data
     * @throws ParserException if some error accused while parsing
     */
    private Article buildArticle(final Element articleElement)
            throws ParserException {
        Article article = new Article();
        // fill Article
        try {
            int id = Integer.parseInt(articleElement.getAttribute(ARTICLE_ID));
            article.setId(id);
        } catch (NumberFormatException exp) {
            String errorMessage = "Article ID is invalid integer format";
            System.err.println(errorMessage + exp.getMessage());
            throw new ParserException(errorMessage + exp.getMessage());
        }
        article.setArticleTitle(getElementTextContent(articleElement,
                ARTICLE_TITLE_TAG));
        article.setAuthor(getElementTextContent(articleElement,
                ARTICLE_AUTHOR_TAG));
        article.setArticleUrl(getElementTextContent(articleElement,
                ARTICLE_URL_TAG));
        // fill hotkeys
        Set<String> hotkeysSet = new HashSet<>();
        Element hotkeysTag =
            (Element) articleElement.getElementsByTagName(ARTICLE_HOTKEYS_TAG)
                    .item(0);
        // take children list of elements <hotkeys>
        NodeList hotkeyList =
                hotkeysTag.getElementsByTagName(ARTICLE_HOTKEYS_HOTKEY_TAG);
        for (int i = 0; i < hotkeyList.getLength(); i++) {
            String hotkey = hotkeyList.item(i).getTextContent();
            hotkeysSet.add(hotkey);
        }
        // set hotkeys to Article
        article.setHotKeys(hotkeysSet);
        return article;
    }

    /**
     * Get text of node.
     * @param element - parent node name
     * @param elementName - node name where is text
     * @return text between tags
     */
    private static String getElementTextContent(final Element element,
                                                final String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
