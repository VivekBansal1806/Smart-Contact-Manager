package org.scm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scm.Helper.Constraints;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
public class Contact {

    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    private String address;
    private String websiteLink;
    private String linkedinLink;

    private String pictureId= Constraints.default_pictureId;
    private String picture;

    @ManyToOne
    private User user;

    //Eager means when we fetch the user, the social links will automatically fetch with user
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<SocialLinks> socialLinks = new ArrayList<>();
}

