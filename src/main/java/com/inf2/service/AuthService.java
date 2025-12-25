package com.inf2.service;

import com.inf2.dao.impl.AdvisorDAOImpl;
import com.inf2.dao.impl.ClientDAOImpl;
import com.inf2.domain.Advisor;
import com.inf2.domain.Client;
import com.inf2.dto.user.LoginRequest;
import com.inf2.dto.user.LoginResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mindrot.jbcrypt.BCrypt;

import javax.security.enterprise.AuthenticationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class AuthService {

    @Inject
    private EntityManagerFactory emf;

    @Inject
    private ClientDAOImpl clientDAO;

    @Inject
    private AdvisorDAOImpl advisorDAO;

    @Inject
    private TokenService tokenService;

    public LoginResponse clientLogin(LoginRequest loginRequest) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Client client = clientDAO.findByEmail(em, loginRequest.getEmail());
            if(client == null) {
                throw new RuntimeException("Client with email " + loginRequest.getEmail() + " not found");
            }else if(!BCrypt.checkpw(loginRequest.getPassword(), client.getPassword())){
                throw new RuntimeException("Wrong password");
            }
            Set<String> roles = new HashSet<>(Collections.singletonList("client"));
            String token = tokenService.generate(client.getEmail(), roles);
            return new LoginResponse(token,3600);
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public LoginResponse advisorLogin(LoginRequest loginRequest) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Advisor advisor = advisorDAO.findByEmail(em, loginRequest.getEmail());
            if(advisor == null) {
                throw new RuntimeException("Client with email " + loginRequest.getEmail() + " not found");
            }else if(!BCrypt.checkpw(loginRequest.getPassword(), advisor.getPassword())){
                throw new RuntimeException("Wrong password");
            }
            Set<String> roles = new HashSet<>(Collections.singletonList("advisor"));
            String token = tokenService.generate(advisor.getEmail(), roles);
            return new LoginResponse(token,3600);
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


}
