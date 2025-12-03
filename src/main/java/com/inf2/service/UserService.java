package com.inf2.service;

import com.inf2.dao.impl.UserDAOImpl;
import com.inf2.domain.User;
import com.inf2.dto.user.UserCreateRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

// step 3 : logique métier - où j'implémente les contraintes métiers
@Singleton
public class UserService {
    @Inject
    private EntityManagerFactory emf;

    @Inject
    private UserDAOImpl userDAO;

    public User createUser(UserCreateRequest userCreateRequest) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            User user = new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName(), userCreateRequest.getEmail(), userCreateRequest.getPassword(), userCreateRequest.getDateOfBirth());
            userDAO.save(em, user);
            transaction.commit();
            return user;
        }catch (Exception e){
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Service failed to save user via DTO");
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
