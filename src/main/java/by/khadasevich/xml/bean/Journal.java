package by.khadasevich.xml.bean;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Journal {
    private String journalTitle;
    private Contacts contacts;
    private Set<Article> articles = new HashSet<>();
}
