package fr.afpa.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


/**
 * Classe utilitaire pour la gestion des tokens JWT.
 * Elle permet de générer, valider et extraire les informations des tokens.
 */
public class JwtUtil {

    /**
     * Clé secrète utilisée pour signer les tokens JWT.
     */
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Durée de vie du token JWT en millisecondes (1 jour).
     */
    private static final long EXPIRATION_TIME = 86400000L; //  (1 jour)

    /**
     * Génère un token JWT pour un utilisateur donné en incluant son login et son rôle.
     *
     * @param login Le login de l'utilisateur.
     * @param role Le rôle de l'utilisateur.
     * @return Le token JWT généré sous forme de chaîne de caractères.
     */
    public static String generateToken(String login, String role) {
        return Jwts.builder()
                .setSubject(login)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration du token
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Valide un token JWT. Vérifie si le token est valide et signé avec la clé secrète.
     *
     * @param token Le token JWT à valider.
     * @return true si le token est valide, sinon false.
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // Token invalide ou expiré
        }
    }

    /**
     * Extrait le login de l'utilisateur à partir du token JWT.
     *
     * @param token Le token JWT.
     * @return Le login de l'utilisateur.
     */
    public static String getLoginFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Extrait le rôle de l'utilisateur à partir du token JWT.
     *
     * @param token Le token JWT.
     * @return Le rôle de l'utilisateur.
     */
    public static String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}