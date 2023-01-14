package edu.logintegra.springsecuritydemo.authorities;

import javax.persistence.*;

@Entity
public class Authority {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    AuthorityName authority;

    public Authority(AuthorityName authority) {
        this.authority = authority;
    }

    public Authority() {

    }

    public Long getId() {
        return id;
    }

    public AuthorityName getAuthority() {
        return authority;
    }
}
