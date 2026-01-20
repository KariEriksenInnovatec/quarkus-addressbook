package net.innovatec.adressebok.application;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AdressebokResourceTest {
    @Test
    void testHentAdressebok() {
                given()
                .contentType("application/json")
                .when()
                .get("/adresseboker")
                .then()
                .statusCode(200);
    }
}
