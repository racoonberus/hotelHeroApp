package com.racoonberus.hotelHero.domain;

import java.io.Serializable;

public class Person
        extends com.racoonberus.tplRegHelper.domain.Person
        implements Serializable {

    public String getFullName() {
        return this.getLastName() + " " + this.getFirstName();
    }

}
