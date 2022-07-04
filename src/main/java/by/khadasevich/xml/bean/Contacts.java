package by.khadasevich.xml.bean;

import lombok.Data;

@Data
public class Contacts {
    /**
     * Journal's address.
     */
    private String address;
    /**
     * Journal's tel.
     */
    private String tel;
    /**
     * Journal's email.
     */
    private String email;
    /**
     * Journal's url.
     */
    private String url;
}
