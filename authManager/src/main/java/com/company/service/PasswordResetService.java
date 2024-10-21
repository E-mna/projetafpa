package fr.afpa.service;

import fr.afpa.core.entity.User;
import fr.afpa.security.util.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/api/auth")
public class PasswordResetService {

    /**
     * Endpoint pour demander une réinitialisation de mot de passe.
     * Envoie un token temporaire de réinitialisation par email.
     *
     * @param email L'email de l'utilisateur ayant oublié son mot de passe.
     * @return Une réponse HTTP indiquant si l'email a été envoyé avec succès.
     */
    @POST
    @Path("/forgot-password")
    @Transactional
    public Response forgotPassword(String email) {
        // Trouve l'utilisateur par email
        User user = User.find("email", email).firstResult();
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Email not found").build();
        }

        // Génère un token temporaire unique (ex: UUID ou JWT)
        String resetToken = UUID.randomUUID().toString();

        // TODO: Envoi de l'email avec le lien de réinitialisation contenant le resetToken
        // sendEmail(user.getEmail(), "Password reset link: .../reset-password?token=" + resetToken);

        // On peut Stocker le token temporaire dans la base (ou dans un système de cache avec expiration)



        return Response.ok("Password reset email sent").build();
    }

    /**
     * Endpoint pour réinitialiser le mot de passe via un token reçu par email.
     *
     * @param token       Le token de réinitialisation.
     * @param newPassword Le nouveau mot de passe choisi.
     * @return Une réponse HTTP confirmant la modification du mot de passe.
     */
    @POST
    @Path("/reset-password")
    @Transactional
    public Response resetPassword(String token, String newPassword) {
        // Ici, il faudrait vérifier le token et sa validité dans le système.
        // Pour l'exemple, on suppose qu'on peut lier un token à un utilisateur.

        // Simule la récupération de l'utilisateur via le token.
        // User user = findUserByToken(token);
        // if (user == null) {
        //     return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build();
        // }

        // Hache le nouveau mot de passe et met à jour l'utilisateur
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        // user.setPwHash(hashedPassword);
        // user.persist();

        return Response.ok("Password reset successfully").build();
    }
}
