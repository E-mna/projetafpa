package fr.afpa.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
    public String login;
    public String email;
    public String password;
    public String role; // Nouveau champ pour le rôle
}
