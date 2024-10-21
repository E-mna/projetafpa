package fr.afpa.core.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


/**
 * Classe représentant l'entité utilisateur.
 * Utilisée pour stocker les informations des utilisateurs dans la base de données.
 */
@Entity
@Getter
@Setter
public class User extends PanacheEntity {

    /**
     * Le login unique de l'utilisateur.
     */
    @Column(name = "LOGIN", unique = true)
    @NotBlank(message = "Le login ne peut pas être vide.")
    private String login;

    /**
     * L'email unique de l'utilisateur.
     */
    @Column(name = "EMAIL", unique = true)
    @NotBlank(message = "L'email ne peut pas être vide.")
    @Email(message = "Email invalide.")
    private String email;

    /**
     * Le hachage du mot de passe de l'utilisateur.
     */
    @Column(name = "PWSHASH")
    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    private String pwHash;

    /**
     * Le rôle de l'utilisateur (ex: USER, ADMIN).
     */
    @Column(name = "ROLE")
    @NotBlank(message = "Le rôle ne peut pas être vide.")
    private String role;
}
