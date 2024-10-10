package fr.afpa.service;

import fr.afpa.core.entity.User;
import fr.afpa.dto.UserDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

@Path("/api/auth")
public class AuthService {

    @POST
    @Path("/signup")
    @Transactional
    public Response signup(@Valid UserDto request) {
        // Vérifier si l'utilisateur existe déjà avec cet email
        if (User.find("email", request.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT).entity("Email already exists").build();
        }

        // Créer un nouvel utilisateur
        User user = new User();
        user.setLogin(request.getLogin()); // Utilisation du champ login
        user.setEmail(request.getEmail());
        user.setRole(request.getRole()); // Assigner le rôle

        // Hacher le mot de passe avant de le sauvegarder
        user.setPwHash(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.persist();

        return Response.status(Response.Status.CREATED).entity("User created successfully").build();
    }
}
