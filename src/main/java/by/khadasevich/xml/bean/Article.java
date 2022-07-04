package by.khadasevich.xml.bean;

import lombok.Data;

import java.util.Set;

@Data
public class Article {
    /**
     * Journal's Article unique id.
     */
    private int id;
    /**
     * Journal's Article title.
     */
    private String articleTitle;
    /**
     * Journal's Article author.
     */
    private String author;
    /**
     * Journal's Article url.
     */
    private String articleUrl;
    /**
     * Journal's Article hotkeys.
     */
    private Set<String> hotKeys;
}
