package fr.afpa;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AuthServiceTest {

    @Test
    public void testSignupSuccess() {
        String json = "{ \"nom\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password123\" }";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/api/auth/signup")
                .then()
                .statusCode(201)
                .body(is("User created successfully"));
    }

    @Test
    public void testSignupEmailAlreadyExists() {
        String json = "{ \"nom\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password123\" }";

        // Inscription initiale
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/api/auth/signup");

        // Essaye de s'inscrire à nouveau
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/api/auth/signup")
                .then()
                .statusCode(409)
                .body(is("Email already exists"));
    }
}
