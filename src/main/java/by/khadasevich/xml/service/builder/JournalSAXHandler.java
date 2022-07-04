package by.khadasevich.xml.service.builder;

import by.khadasevich.xml.bean.Article;
import by.khadasevich.xml.bean.Contacts;
import by.khadasevich.xml.bean.Journal;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashSet;

public class JournalSAXHandler extends DefaultHandler {
    /**
     * Tag name <journal>.
     */
    private static final String JOURNAL_TAG = "journal";
    /**
     * Tag name <title>.
     */
    private static final String TITLE_TAG = "title";
    /**
     * Journal <contacts> tag.
     */
    private static final String JOURNAL_CONTACTS = "contacts";
    /**
     * Tag name <address>.
     */
    private static final String ADDRESS_TAG = "address";
    /**
     * Tag name <tel>.
     */
    private static final String TEL_TAG = "tel";
    /**
     * Tag name <email>.
     */
    private static final String EMAIL_TAG = "email";
    /**
     * Tag name <url>.
     */
    private static final String URL_TAG = "url";
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
     * Tag name <author>.
     */
    private static final String AUTHOR_TAG = "author";
    /**
     * Article <hotkeys> tag name.
     */
    private static final String ARTICLE_HOTKEYS_TAG = "hotkeys";
    /**
     * Tag name <hotkey>.
     */
    private static final String HOTKEY_TAG = "hotkey";
    /**
     * Journal instance to build while SAX parsing xml data file.
     */
    private Journal journal;
    /**
     * Current start tag name that XML reader read.
     */
    private String currentTag;
    /**
     * Indicate if <article> tag start.
     */
    private boolean isArticle;
    /**
     * Journal's Article to fill data from xml data file.
     */
    private Article article;
    /**
     * Get Journal instance.
     * @return Journal instance with filled data from xml file
     */
    public Journal getJournal() {
        return journal;
    }

    /**
     * Receive notification of the start of an element.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the start of
     * each element (such as allocating a new tree node or writing
     * output to a file).</p>
     *
     * @param uri        The Namespace URI, or the empty string if the
     *                   element has no Namespace URI or if Namespace
     *                   processing is not being performed.
     * @param localName  The local name (without prefix), or the
     *                   empty string if Namespace processing is not being
     *                   performed.
     * @param qName      The qualified name (with prefix), or the
     *                   empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If
     *                   there are no attributes, it shall be an empty
     *                   Attributes object.
     * @throws SAXException Any SAX exception, possibly
     *                      wrapping another exception.
     * @see ContentHandler#startElement
     */
    @Override
    public void startElement(final String uri, final String localName,
                             final String qName, final Attributes attributes)
            throws SAXException {
        switch (localName) {
            case JOURNAL_TAG -> {
                this.journal = new Journal();
                isArticle = false;
            }
            case JOURNAL_CONTACTS -> this.journal.setContacts(new Contacts());
            case JOURNAL_ARTICLES_TAG -> {
                this.journal.setArticles(new HashSet<>());
                isArticle = true;
            }
            case ARTICLE_TAG -> {
                this.article = new Article();
                try {
                    int articleId =
                            Integer.parseInt(attributes.getValue(ARTICLE_ID));
                    this.article.setId(articleId);
                } catch (NumberFormatException exp) {
                    throw new SAXException("Invalid integer format article id. "
                            + exp.getMessage());
                }
            }
            case ARTICLE_HOTKEYS_TAG ->
                    this.article.setHotKeys(new HashSet<>());
            default -> this.currentTag = localName;
        }
    }

    /**
     * Receive notification of the end of an element.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the end of
     * each element (such as finalising a tree node or writing
     * output to a file).</p>
     *
     * @param uri       The Namespace URI, or the empty string if the
     *                  element has no Namespace URI or if Namespace
     *                  processing is not being performed.
     * @param localName The local name (without prefix), or the
     *                  empty string if Namespace processing is not being
     *                  performed.
     * @param qName     The qualified name (with prefix), or the
     *                  empty string if qualified names are not available.
     * @throws SAXException Any SAX exception, possibly
     *                      wrapping another exception.
     * @see ContentHandler#endElement
     */
    @Override
    public void endElement(final String uri, final String localName,
                           final String qName) throws SAXException {
        if (ARTICLE_TAG.equals(localName)) {
            this.journal.getArticles().add(this.article);
        }
    }

    /**
     * Receive notification of character data inside an element.
     *
     * <p>By default, do nothing.  Application writers may override this
     * method to take specific actions for each chunk of character data
     * (such as adding the data to a node or buffer, or printing it to
     * a file).</p>
     *
     * @param ch     The characters.
     * @param start  The start position in the character array.
     * @param length The number of characters to use from the
     *               character array.
     * @throws SAXException Any SAX exception, possibly
     *                      wrapping another exception.
     * @see ContentHandler#characters
     */
    @Override
    public void characters(final char[] ch, final int start,
                           final int length) throws SAXException {
        String tagText = new String(ch, start, length).trim();
        switch (this.currentTag) {
            case TITLE_TAG:
                if (isArticle) {
                    this.article.setArticleTitle(tagText);
                } else {
                    this.journal.setJournalTitle(tagText);
                }
                break;
            case ADDRESS_TAG:
                this.journal.getContacts().setAddress(tagText);
                break;
            case TEL_TAG:
                this.journal.getContacts().setTel(tagText);
                break;
            case EMAIL_TAG:
                this.journal.getContacts().setEmail(tagText);
                break;
            case URL_TAG:
                if (isArticle) {
                    this.article.setArticleUrl(tagText);
                } else {
                    this.journal.getContacts().setUrl(tagText);
                }
                break;
            case HOTKEY_TAG:
                this.article.getHotKeys().add(tagText);
                break;
            case AUTHOR_TAG:
                this.article.setAuthor(tagText);
                break;
        }
    }
}
