package com.racoonberus.hotelHero.domain;

import javax.persistence.*;

@Entity
@Table(name = "contact")
public class Contact {

    public enum Types {EMAIL, MOBILE_PHONE}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private Types type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Contact() {
    }

    public Contact(String value, Types type, User user) {
        this.value = value;
        this.type = type;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Contact setId(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Contact setValue(String value) {
        this.value = value;
        return this;
    }

    public Types getType() {
        return type;
    }

    public Contact setType(Types type) {
        this.type = type;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Contact setUser(User user) {
        this.user = user;
        return this;
    }

}
