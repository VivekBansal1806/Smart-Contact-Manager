package org.scm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User implements UserDetails {

    @Id
    private String userId;

    @Column(name = "userName", nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String email;
    private String profilePic;
    private String phoneNumber;
    @Column(length = 1000)
    private String about;

    //validation
    private boolean enabled = true;
    private boolean emailVerified = false;
    private boolean phoneNumberEnabled = false;

    //provider
    @Enumerated(value=EnumType.STRING)
    private Providers providers=Providers.SELF;
    private int providerId;


    // mapping of user to contacts
    // this user is field of Contact class which can ensure that that is no bidirectional mapping
    // CascadeType.ALL -> if the user is deleted then it's all contacts will be deleted.
    // FetchType.LAZY means when we fetch the user then contacts can't fetch automatically
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Contact> contacts = new ArrayList<>();

@ElementCollection(fetch = FetchType.EAGER)
    private List<String> rolesList = new ArrayList<>();

    /// ////////////////////////////////////////////////////////////
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles=rolesList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
