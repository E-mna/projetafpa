package fr.afpa.service;

import fr.afpa.core.entity.User;
import fr.afpa.dto.UpdatePasswordDto;
import fr.afpa.dto.UpdateUserDto;
import fr.afpa.security.util.JwtUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Service responsable de la gestion des utilisateurs : mise à jour du mot de passe et des coordonnées.
 */
@Path("/api/user")
public class UserService {

    /**
     * Endpoint pour modifier le mot de passe de l'utilisateur.
     *
     * @param updatePasswordDto DTO contenant l'ancien et le nouveau mot de passe.
     * @param headers           Les en-têtes HTTP pour extraire le token JWT.
     * @return Une réponse HTTP indiquant si la modification est réussie ou non.
     */
    @PUT
    @Path("/password")
    @Transactional
    @RolesAllowed({"USER", "ADMIN"})  // Seuls les utilisateurs authentifiés peuvent modifier leur mot de passe
    public Response updatePassword(@Valid UpdatePasswordDto updatePasswordDto, @Context HttpHeaders headers) {
        // Extraire et valider le token JWT
        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token is missing").build();
        }

        String token = authHeader.substring("Bearer".length()).trim();
        if (!JwtUtil.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        // Récupérer l'utilisateur via son login
        String login = JwtUtil.getLoginFromToken(token);
        User user = User.find("login", login).firstResult();

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        // Vérifier l'ancien mot de passe
        if (!BCrypt.checkpw(updatePasswordDto.getOldPassword(), user.getPwHash())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect current password").build();
        }

        // Hacher et mettre à jour le nouveau mot de passe
        String newHashedPassword = BCrypt.hashpw(updatePasswordDto.getNewPassword(), BCrypt.gensalt());
        user.setPwHash(newHashedPassword);
        user.persist();

        return Response.ok("Password updated successfully").build();
    }

    /**
     * Endpoint pour modifier les coordonnées de l'utilisateur (email, login).
     *
     * @param updateUserDto DTO contenant les nouvelles informations (email, login).
     * @param headers       Les en-têtes HTTP pour extraire le token JWT.
     * @return Une réponse HTTP indiquant si la modification est réussie ou non.
     */
    @PUT
    @Path("/details")
    @Transactional
    @RolesAllowed({"USER", "ADMIN"})  // Seuls les utilisateurs authentifiés peuvent modifier leurs coordonnées
    public Response updateUserDetails(@Valid UpdateUserDto updateUserDto, @Context HttpHeaders headers) {
        // Extraire et valider le token JWT
        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token is missing").build();
        }

        String token = authHeader.substring("Bearer".length()).trim();
        if (!JwtUtil.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        // Récupérer l'utilisateur via son login
        String login = JwtUtil.getLoginFromToken(token);
        User user = User.find("login", login).firstResult();

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        // Vérifier si l'email ou le login est déjà utilisé
        if (!user.getEmail().equals(updateUserDto.getEmail()) && User.find("email", updateUserDto.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT).entity("Email already exists").build();
        }

        if (!user.getLogin().equals(updateUserDto.getLogin()) && User.find("login", updateUserDto.getLogin()).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT).entity("Login already exists").build();
        }

        // Mettre à jour les nouvelles coordonnées
        user.setEmail(updateUserDto.getEmail());
        user.setLogin(updateUserDto.getLogin());
        user.persist();

        return Response.ok("User details updated successfully").build();
    }
}
