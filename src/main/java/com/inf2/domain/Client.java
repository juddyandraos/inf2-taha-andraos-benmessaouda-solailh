package com.inf2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "clients")
public class Client extends User {
    public Client(String firstName, String lastName, String email, String password, Date dateOfBirth) {
        super(firstName, lastName, email, password, dateOfBirth);
    }

    public Client() {
        super();
    }
}
