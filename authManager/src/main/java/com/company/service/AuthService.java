package fr.afpa.service;

import fr.afpa.core.entity.User;
import fr.afpa.dto.UserDto;
import fr.afpa.security.util.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Service responsable de l'authentification et de l'inscription des utilisateurs.
 * Fournit des endpoints pour la création de comptes et la génération de tokens JWT.
 */
@Path("/api/auth")
public class AuthService {

    /**
     * Endpoint pour l'inscription d'un nouvel utilisateur.
     * Cette méthode vérifie si l'utilisateur existe déjà, puis crée un nouvel utilisateur
     * avec un mot de passe haché et génère un token JWT pour celui-ci.
     *
     * @param request Le DTO contenant les informations de l'utilisateur.
     * @return Une réponse HTTP avec un statut de création (201) et un token JWT en cas de succès.
     */
    @POST
    @Path("/signup")
    @Transactional
    public Response signup(@Valid UserDto request) {
        // Vérifie si l'utilisateur existe déjà avec cet email ou login
        if (User.find("email", request.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT).entity("Email already exists").build();
        }
        if (User.find("login", request.getLogin()).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT).entity("Login already exists").build();
        }

        // Créee un nouvel utilisateur
        User user = new User();
        user.setLogin(request.getLogin());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        // Hache le mot de passe
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        user.setPwHash(hashedPassword);
        user.persist();

        // Génére un JWT pour l'utilisateur
        String token = JwtUtil.generateToken(user.getLogin(), user.getRole());

        // Retourne une réponse avec le token
        return Response.status(Response.Status.CREATED)
                .entity("User created successfully. Token: " + token)
                .build();
    }
}
