package fr.afpa.security.controller;

import fr.afpa.security.util.JwtUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

/**
 * Classe fournissant un endpoint sécurisé accessible uniquement aux utilisateurs ayant un rôle spécifique.
 * Ce endpoint nécessite un token JWT valide pour y accéder.
 */
@Path("/api/protected")
public class SecureEndpoint {

    /**
     * Endpoint pour accéder à une ressource protégée.
     * Ce endpoint est accessible uniquement aux utilisateurs avec les rôles "USER" ou "ADMIN".
     * Le token JWT est extrait de l'en-tête Authorization.
     *
     * @param headers Les en-têtes HTTP de la requête (pour extraire le token JWT).
     * @return Une réponse HTTP indiquant si l'accès est accordé ou refusé.
     */
    @GET
    @RolesAllowed({"USER", "ADMIN"})  // Autorisation basée sur les rôles
    public Response getProtectedResource(@Context HttpHeaders headers) {
        // Extraire le token JWT de l'en-tête Authorization
        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token is missing").build();
        }

        String token = authHeader.substring("Bearer".length()).trim();

        // Valider le token
        if (!JwtUtil.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        // Récupére les informations du token
        String login = JwtUtil.getLoginFromToken(token);
        String role = JwtUtil.getRoleFromToken(token);

        // Retourne la ressource protégée
        return Response.ok("Access granted for user: " + login + " with role: " + role).build();
    }
}
