package fr.afpa;

import fr.afpa.security.util.JwtUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    @Test
    public void testGenerateToken() {
        // Générer un token avec un login et un rôle
        String token = JwtUtil.generateToken("testUser", "USER");

        // Vérifier que le token n'est pas nul
        assertNotNull(token);
    }

    @Test
    public void testValidateToken() {
        // Générer un token valide
        String token = JwtUtil.generateToken("testUser", "USER");

        // Vérifier que le token est valide
        assertTrue(JwtUtil.validateToken(token));
    }

    @Test
    public void testValidateInvalidToken() {
        // Vérifier que la validation échoue pour un token invalide
        assertFalse(JwtUtil.validateToken("invalidToken"));
    }

    @Test
    public void testGetLoginFromToken() {
        // Générer un token avec un login
        String token = JwtUtil.generateToken("testUser", "USER");

        // Extraire le login du token
        String login = JwtUtil.getLoginFromToken(token);

        // Vérifier que le login est correct
        assertEquals("testUser", login);
    }

    @Test
    public void testGetRoleFromToken() {
        // Générer un token avec un rôle
        String token = JwtUtil.generateToken("testUser", "ADMIN");

        // Extraire le rôle du token
        String role = JwtUtil.getRoleFromToken(token);

        // Vérifier que le rôle est correct
        assertEquals("ADMIN", role);
    }
}