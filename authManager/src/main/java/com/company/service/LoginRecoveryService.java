package fr.afpa.service;

import fr.afpa.core.entity.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
public class LoginRecoveryService {

    /**
     * Endpoint pour récupérer le login d'un utilisateur en cas d'oubli.
     *
     * @param email L'email de l'utilisateur qui a oublié son login.
     * @return Une réponse HTTP avec le login envoyé par email.
     */
    @POST
    @Path("/recover-login")
    @Transactional
    public Response recoverLogin(String email) {
        // Trouve l'utilisateur par email
        User user = User.find("email", email).firstResult();
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Email not found").build();
        }

        // TODO: Envoi du login par email (ici on envoie simplement une réponse)
        // sendEmail(user.getEmail(), "Your login is: " + user.getLogin());

        return Response.ok("Login sent to your email").build();
    }
}
