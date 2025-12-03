package com.inf2.dao.impl;

import com.inf2.domain.User;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

@Singleton
public class UserDAOImpl { //step 2 : does the database operations (insert, find etc) --> couche qui intéragit directement avec la bdd
    public User save (EntityManager em, User user) {
        em.persist(user);
        return user;
    }
}
