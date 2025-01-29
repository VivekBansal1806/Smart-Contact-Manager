package org.scm.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SocialLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String link;
    private String title;

    @ManyToOne
    private Contact contact;
}
