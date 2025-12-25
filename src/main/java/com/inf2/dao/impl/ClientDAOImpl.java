package com.inf2.dao.impl;

import com.inf2.domain.Client;
import com.inf2.dto.user.UserUpdateRequest;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
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

    public void update(EntityManager em, UUID id, UserUpdateRequest userUpdateRequest) {
        Client client = em.find(Client.class, id);
        client.setEmail(userUpdateRequest.getEmail());
        em.persist(client);
    }

    public Client findByEmail(EntityManager em, String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> client = query.from(Client.class);

        Predicate clientEmailPredicate = cb.equal(client.get("email"), email);
        query.select(client).where(clientEmailPredicate);

        Client result = em.createQuery(query).getSingleResult();
        return result;
    }
}
