package com.inf2.dao.impl;

import com.inf2.domain.Advisor;
import com.inf2.dto.user.UserUpdateRequest;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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
        em.persist(advisor);
    }

    public Advisor findByEmail(EntityManager em, String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Advisor> query = cb.createQuery(Advisor.class);
        Root<Advisor> advisor = query.from(Advisor.class);

        Predicate advisorEmailPredicate = cb.equal(advisor.get("email"), email);
        query.select(advisor).where(advisorEmailPredicate);

        Advisor result = em.createQuery(query).getSingleResult();
        return result;
    }
}
