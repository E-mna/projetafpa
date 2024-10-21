package fr.afpa.service;

import fr.afpa.core.entity.User;
import fr.afpa.security.util.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

@Path("/api/auth")
public class TwoFactorAuthService {

    /**
     * Première étape de l'authentification (login et mot de passe).
     * Envoie un code de vérification à l'utilisateur si le login et mot de passe sont valides.
     *
     * @param login Le login de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @return Un token temporaire ou une erreur en cas d'échec de l'authentification.
     */
    @POST
    @Path("/login")
    @Transactional
    public Response login(String login, String password) {
        // Vérifie l'utilisateur dans la base
        User user = User.find("login", login).firstResult();
        if (user == null || !BCrypt.checkpw(password, user.getPwHash())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid login or password").build();
        }

        // Génère un code de vérification (à envoyer par email ou SMS)
        String verificationCode = String.valueOf((int) (Math.random() * 9000) + 1000);

        // TODO: Envoi du code par email/SMS

        // Stocke ce code temporairement dans un système de cache (Redis, DB...)
        // storeVerificationCodeForUser(user, verificationCode);

        return Response.ok("Verification code sent").build();
    }

    /**
     * Deuxième étape de l'authentification (vérification du code 2FA).
     *
     * @param login Le login de l'utilisateur.
     * @param code Le code de vérification envoyé par email/SMS.
     * @return Le token JWT complet si la vérification est réussie.
     */
    @POST
    @Path("/verify-2fa")
    @Transactional
    public Response verify2FA(String login, String code) {
        // Récupère l'utilisateur par login
        User user = User.find("login", login).firstResult();
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid login").build();
        }

        // Vérifie que le code correspond à celui stocké pour cet utilisateur
        // if (!isVerificationCodeValid(user, code)) {
        //     return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid verification code").build();
        // }

        // Génére un token JWT après une double authentification réussie
        String token = JwtUtil.generateToken(user.getLogin(), user.getRole());

        return Response.ok("JWT Token: " + token).build();
    }
}
