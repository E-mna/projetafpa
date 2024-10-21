package fr.afpa.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilisé pour transférer les informations de l'utilisateur lors des requêtes HTTP.
 */
@Getter
@Setter
public class UserDto {
    /**
     * Le login de l'utilisateur.
     */
    public String login;

    /**
     * L'email de l'utilisateur.
     */
    public String email;

    /**
     * Le mot de passe de l'utilisateur.
     */
    public String password;

    /**
     * Le rôle de l'utilisateur.
     */
    public String role;
}
