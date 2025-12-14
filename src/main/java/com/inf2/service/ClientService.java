package com.inf2.service;

import com.inf2.dao.impl.ClientDAOImpl;
import com.inf2.domain.Client;
import com.inf2.dto.user.UserCreateRequest;
import com.inf2.dto.user.UserUpdateRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

// step 3 : logique métier - où j'implémente les contraintes métiers
@Singleton
public class ClientService {
    @Inject
    private EntityManagerFactory emf;

    @Inject
    private ClientDAOImpl userDAO;

    public Client createClient(UserCreateRequest userCreateRequest) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Client user = new Client(userCreateRequest.getFirstName(), userCreateRequest.getLastName(), userCreateRequest.getEmail(), userCreateRequest.getPassword(), userCreateRequest.getDateOfBirth());
            userDAO.save(em, user);
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

    public Client getClientById(UUID id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return userDAO.find(em, id);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void deleteClient(UUID id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            userDAO.delete(em, id);
            transaction.commit();
        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Service failed to delete client");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void updateClient(UUID id, UserUpdateRequest userUpdateRequest){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            userDAO.update(em, id, userUpdateRequest);
            transaction.commit();
        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Service failed to update client");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
