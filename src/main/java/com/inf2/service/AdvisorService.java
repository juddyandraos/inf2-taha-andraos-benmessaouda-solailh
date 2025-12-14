package com.inf2.service;

import com.inf2.dao.impl.AdvisorDAOImpl;
import com.inf2.domain.Advisor;
import com.inf2.dto.user.UserCreateRequest;
import com.inf2.dto.user.UserUpdateRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

@Singleton
public class AdvisorService {
    @Inject
    private EntityManagerFactory emf;

    @Inject
    private AdvisorDAOImpl advisorDAO;

    public Advisor createAdvisor(UserCreateRequest userCreateRequest) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Advisor user = new Advisor(userCreateRequest.getFirstName(), userCreateRequest.getLastName(), userCreateRequest.getEmail(), userCreateRequest.getPassword(), userCreateRequest.getDateOfBirth());
            advisorDAO.save(em, user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Service failed to save user via DTO");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Advisor getAdvisorById(UUID id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return advisorDAO.find(em, id);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void deleteAdvisor(UUID id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            advisorDAO.delete(em, id);
            transaction.commit();
        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Service failed to delete advisor");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void updateAdvisor(UUID id, UserUpdateRequest userUpdateRequest){
        EntityManager em = null; //to access it not only in the try
        try {
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            advisorDAO.update(em, id, userUpdateRequest);
            transaction.commit();
        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Service failed to update advisor");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
