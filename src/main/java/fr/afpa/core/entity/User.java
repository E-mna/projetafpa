package fr.afpa.core.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends PanacheEntity {

    @Column(name = "LOGIN")
    @NotBlank(message = "Le login ne peut pas être vide.")
    private String login;

    @Column(name = "EMAIL")
    @NotBlank(message = "L'email ne peut pas être vide.")
    @Email(message = "Email invalide.")
    private String email; // Correspond à la colonne EMAIL

    @Column(name = "PWSHASH")
    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    private String pwHash;

    @Column(name = "ROLE")
    @NotBlank(message = "Le rôle ne peut pas être vide.")
    private String role;
}
