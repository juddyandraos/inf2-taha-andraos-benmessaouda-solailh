package com.inf2.dao.impl;

import com.inf2.domain.Advisor;
import com.inf2.domain.Client;
import com.inf2.dto.user.UserUpdateRequest;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

import java.util.UUID;

@Singleton
public class AdvisorDAOImpl {
    public Advisor save(EntityManager em, Advisor user) {
        em.persist(user);
        return user;
    }

    public Advisor find(EntityManager em, UUID id) {
        return em.find(Advisor.class, id);
    }

    public void delete(EntityManager em, UUID id) {
        em.remove(em.find(Advisor.class, id));
    }

    public void  update(EntityManager em, UUID id, UserUpdateRequest userUpdateRequest) {
        Advisor advisor = em.find(Advisor.class, id);
        advisor.setEmail(userUpdateRequest.getEmail());
        advisor.setPassword(userUpdateRequest.getPassword());
        em.persist(advisor);
    }
}
