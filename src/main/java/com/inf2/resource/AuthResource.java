package com.inf2.resource;

import com.inf2.dto.user.JwkKeyDTO;
import com.inf2.dto.user.JwksResponseDTO;
import com.inf2.dto.user.LoginRequest;
import com.inf2.service.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    String publicKeyLocation = "/publicKey.pem";

    @Inject
    private AuthService authService;

    @POST
    @Path("/client/login")
    public Response loginClient(@Valid LoginRequest credentials) {
        try{
            return Response.ok(authService.clientLogin(credentials)).build();
        }catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/advisor/login")
    public Response loginAdvisor(@Valid LoginRequest credentials) {
        try{
            return Response.ok(authService.advisorLogin(credentials)).build();
        }catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("/jwks.json")
    public JwksResponseDTO getJwkSet() {
        JwksResponseDTO response = new JwksResponseDTO();

        try {
            // 1. Read and Parse Key
            RSAPublicKey publicKey = loadPublicKey(publicKeyLocation);

            // 2. Convert to Base64URL (Spec requires NO PADDING)
            String n = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(publicKey.getModulus().toByteArray());

            String e = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(publicKey.getPublicExponent().toByteArray());

            // 3. Create DTO and add to list
            // "default-key-id" should match the 'kid' you use when generating tokens
            //TODO: CLEAN UP AND MOVE TO SERVICE
            JwkKeyDTO keyDto = new JwkKeyDTO("1", n, e);

            response.addKey(keyDto);

        } catch (Exception ex) {
            // Log error in real app
            ex.printStackTrace();
            // In case of error, you might want to throw a WebApplicationException
            // or return an empty list depending on your strategy
        }

        return response;
    }

    // Helper to read file and convert to RSAPublicKey
    private RSAPublicKey loadPublicKey(String location) throws Exception {
        InputStream stream = getClass().getResourceAsStream(location);
        if (stream == null) throw new RuntimeException("Key not found: " + location);

        String pem = new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining(""))
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\s", "");

        byte[] encoded = Base64.getDecoder().decode(pem);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
    }


}
