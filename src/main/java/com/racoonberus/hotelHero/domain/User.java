package com.racoonberus.hotelHero.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @OrderBy("id DESC")
    private List<Contact> contacts;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    public User() {
        contacts = new ArrayList<>();
    }

    public User(String email, String password) {
        this();
        this.password = password;
        this.setEmail(email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.equals(this.email))
            this.contacts.add(new Contact(email, Contact.Types.EMAIL, this));
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        if (mobilePhone != null && !mobilePhone.equals(this.mobilePhone))
            this.contacts.add(new Contact(mobilePhone, Contact.Types.MOBILE_PHONE, this));
        this.mobilePhone = mobilePhone;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public User setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        for (Contact c : this.contacts) {
            c.setUser(this);
        }
        return this;
    }

    /*public String getEmail() {
        Contact contact = getContactByType(Contact.Types.EMAIL);
        return contact != null ? contact.getValue() : null;
    }

    public void setEmail(String emailVal) {
        Contact email = new Contact(emailVal, Contact.Types.EMAIL);
        email.setUser(this);
        this.contacts.add(email);
    }

    public String getMobilePhone() {
        Contact contact = getContactByType(Contact.Types.MOBILE_PHONE);
        return contact != null ? contact.getValue() : null;
    }

    public void setMobilePhone(String mobilePhoneVal) {
        Contact mobilePhone = new Contact(mobilePhoneVal, Contact.Types.MOBILE_PHONE);
        mobilePhone.setUser(this);
        this.contacts.add(mobilePhone);
    }*/

    /*private Contact getContactByType(Contact.Types type) {
        List<Contact> list = contacts.subList(0, contacts.size());
        Collections.reverse(list);
        for (Contact contact : list) {
            if (contact.getType() == type)
                return contact;
        }
        return null;
    }*/

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public User setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getUsername() {
        if (getEmail() != null) return getEmail();
        if (getMobilePhone() != null) return getMobilePhone();
        return String.valueOf(id);
    }
}
