package by.khadasevich.xml.service.builder;

import by.khadasevich.xml.bean.Article;
import by.khadasevich.xml.bean.Contacts;
import by.khadasevich.xml.bean.Journal;
import by.khadasevich.xml.service.parser.ParserException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class JournalStAXBuilderTest {
    /**
     * XML file name with Journal data.
     */
    private static final String XML_FILE_NAME = "journal.xml";
    /**
     * XSD schema file name of XML file with Journal data.
     */
    private static final String XSD_FILE_NAME = "journal.xsd";
    /**
     * Journal instance to be filed by data from xml data file.
     */
    private static Journal journal;

    @BeforeAll
    static public void fillJournalFieldsBySAXParser() {
        try {
            JournalStAXBuilder stAXBuilder = new JournalStAXBuilder();
            journal = stAXBuilder.buildJournal(XML_FILE_NAME, XSD_FILE_NAME);
        } catch (ParserException exp) {
            System.err.println(exp.getMessage());
            journal = null;
        }
    }

    @Test
    @DisplayName("Check if after xml data file StAX parsing created the instance of Journal")
    public void testJournalIsInstanceOfJournal() {
        assertThat(journal)
                .as("Journal doesn't created after StAX parsing xml")
                .isInstanceOf(Journal.class);
    }
    @Test
    @DisplayName("Journal after StAX parsing has Title")
    public void testJournalHasTitle() {
        assertThat(journal.getJournalTitle())
                .as("Journal doesn't have title").isNotEmpty();

    }
    @Test
    @DisplayName("Journal after StAX parsing has Contacts")
    public void testJournalHasContacts() {
        Contacts contacts = journal.getContacts();
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(contacts.getAddress())
                .as("Journal Contacts doesn't have address")
                .isNotEmpty();
        soft.assertThat(contacts.getTel())
                .as("Journal Contacts doesn't have tel")
                .isNotEmpty();
        soft.assertThat(contacts.getEmail())
                .as("Journal Contacts doesn't have email")
                .isNotEmpty();
        soft.assertThat(contacts.getUrl())
                .as("Journal Contacts doesn't have url")
                .isNotEmpty();
        soft.assertAll();

    }
    @Test
    @DisplayName("Journal after StAX parsing has two articles")
    public void testJournalHasAllArticles() {
        Set<Article> articleSet = journal.getArticles();
        assertThat(articleSet).as("Journal doesn't have two articles")
                .hasSize(2);
    }
    @Test
    @DisplayName("Journal after StAX parsing has Articles with filed fields")
    public void testJournalArticleHasAllFields() {
        Article article = (Article) journal.getArticles().toArray()[0];
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(article.getId())
                .as("Journal's Article doesn't have id").isPositive();
        soft.assertThat(article.getArticleTitle())
                .as("Journal Article doesn't have title")
                .isNotEmpty();
        soft.assertThat(article.getAuthor())
                .as("Journal Article doesn't have author")
                .isNotEmpty();
        soft.assertThat(article.getArticleUrl())
                .as("Journal Article doesn't have url")
                .isNotEmpty();
        soft.assertThat(article.getHotKeys())
                .as("Journal Article doesn't have hotkeys")
                .hasSizeBetween(2, 3);
        soft.assertAll();
    }
}
