package fr.afpa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour la modification des coordonnées de l'utilisateur (email, login).
 */
public class UpdateUserDto {

    @NotBlank(message = "Le login ne peut pas être vide.")
    private String login;

    @Email(message = "Email invalide.")
    @NotBlank(message = "L'email ne peut pas être vide.")
    private String email;

    // Getters et setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
