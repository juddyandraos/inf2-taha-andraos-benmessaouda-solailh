package com.inf2.dao.impl;

import com.inf2.domain.Client;
import com.inf2.dto.user.UserUpdateRequest;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.util.UUID;

@Singleton
public class ClientDAOImpl { //step 2 : does the database operations (insert, find etc) --> couche qui intéragit directement avec la bdd
    public Client save(EntityManager em, Client user) {
        em.persist(user);
        return user;
    }

    public Client find(EntityManager em, UUID id) {
        return em.find(Client.class, id);
    }

    public void delete(EntityManager em, UUID id) {
        em.remove(em.find(Client.class, id));
    }

    public void  update(EntityManager em, UUID id, UserUpdateRequest userUpdateRequest) {
        Client client = em.find(Client.class, id);
        client.setEmail(userUpdateRequest.getEmail());
        client.setPassword(userUpdateRequest.getPassword());
        em.persist(client);
    }
}
