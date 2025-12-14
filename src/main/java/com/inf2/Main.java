package com.inf2;

import com.inf2.dao.impl.AdvisorDAOImpl;
import com.inf2.dao.impl.HelloDAOImpl;
import com.inf2.dao.impl.ClientDAOImpl;
import com.inf2.service.AdvisorService;
import com.inf2.service.HelloService;
import com.inf2.service.ClientService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.net.URI;

public class Main {
    // The base URI where the JAX-RS application is deployed
    public static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("starterPU");

        final ResourceConfig rc = new ResourceConfig()
                .packages("com.inf2.dao")
                .packages("com.inf2.dao.impl")
                .packages("com.inf2.domain")
                .packages("com.inf2.resource")
                .packages("com.inf2.service")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(emf).to(EntityManagerFactory.class).in(jakarta.inject.Singleton.class);
// 3. Bind the SPECIFIC DAO Implementation to its Interface
                        bind(HelloDAOImpl.class).to(HelloDAOImpl.class).in(jakarta.inject.Singleton.class);
                        bind(ClientDAOImpl.class).to(ClientDAOImpl.class).in(jakarta.inject.Singleton.class);
                        bind(AdvisorDAOImpl.class).to(AdvisorDAOImpl.class).in(jakarta.inject.Singleton.class);
// 4. Bind the Service (optional if scanning is reliable)
                        bind(HelloService.class).to(HelloService.class).in(jakarta.inject.Singleton.class);
                        bind(ClientService.class).to(ClientService.class).in(jakarta.inject.Singleton.class);
                        bind(AdvisorService.class).to(AdvisorService.class).in(jakarta.inject.Singleton.class);
                    }
                });
//resources are auto-binded so no need to bind them here

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws Exception {
        // Start the server with the corrected configuration
        final HttpServer server = startServer();

        System.out.println("Api server is starting on " + BASE_URI);
        System.out.println("Press Ctrl+C to stop the server.");

        // Keep the main thread running until the application is shut down
        Thread.currentThread().join();

        // Clean up on exit
        server.shutdownNow();
    }
}