package com.inf2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "advisors")
public class Advisor extends User{
    public Advisor(String firstName, String lastName, String email, String password, Date dateOfBirth) {
        super(firstName, lastName, email, password, dateOfBirth);
    }

    public Advisor() {
        super();
    }
}
