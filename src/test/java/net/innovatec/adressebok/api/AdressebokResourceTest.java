package net.innovatec.adressebok.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import net.innovatec.adressebok.infrastructure.api.AdressebokResourceImpl;
import net.innovatec.adressebok.infrastructure.api.beans.AdressebokId;
import net.innovatec.adressebok.infrastructure.api.beans.Epost;
import net.innovatec.adressebok.infrastructure.api.beans.KontaktData;
import net.innovatec.adressebok.infrastructure.api.beans.Navn;

@QuarkusTest
class AdressebokResourceTest {
    @Inject
    private AdressebokResourceImpl resource;
          
    private AdressebokId adressebokId;
    private static ObjectMapper objectMapper;
    
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/"; 
        objectMapper = new ObjectMapper();        
    }
    
    @BeforeEach
    void setupForEach() {
        adressebokId = resource.opprettAdressebok();
    }
    
    @Test
    void testHentAdressebokSomEksisterer() {
        given().
            header("adressebokId", adressebokId.getValue().toString()).
        when().
            get("/adressebok").
        then().
            statusCode(200);
    }
        
    @Test
    void testHentAdressebokSomIkkeFinnes() {
        given().
            header("adressebokId", "00000000-0000-0000-0000-000000000000").
        when().
            get("/adressebok").
        then().
            statusCode(404);
    }
    
    @Test
    void testHentAdressebokUgyldigId() {
        given().
            header("adressebokId", "1").
        when().
            get("/adressebok").
        then().
            statusCode(400);
    }

    @Test
    void testOpprettKontaktMedNavn() {
        try {
            KontaktData data = createKontakt("Mickey", "Mouse");
            String jsonBody = objectMapper.writeValueAsString(data);
            given()
                .header("adressebokId", adressebokId.getValue().toString())
                .header("content-type","application/json")
                .body(jsonBody)
            .when()
                .post("/adressebok/{adressebokid}/kontakt", adressebokId.getValue())
            .then()
                .statusCode(200);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        
    }

    @Test
    void testOpprettKontaktIngenFornavn() {
        try {
            KontaktData data = createKontakt(null, "Mouse");            
            String jsonBody = objectMapper.writeValueAsString(data);
            given()
                .header("adressebokId", adressebokId.getValue().toString())
                .header("content-type","application/json")
                .body(jsonBody)
            .when()
                .post("/adressebok/{adressebokid}/kontakt", adressebokId.getValue())
            .then()
                .statusCode(400)
                .body("violations.message[0]", is("must not be null"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        
    }

    @Test
    void testOpprettKontaktTomFornavn() {
        try {
            KontaktData data = createKontakt("", "Mouse");
            String jsonBody = objectMapper.writeValueAsString(data);
            given()
                .header("adressebokId", adressebokId.getValue().toString())
                .header("content-type","application/json")
                .body(jsonBody)
            .when()
                .post("/adressebok/{adressebokid}/kontakt", adressebokId.getValue())
            .then()
                .statusCode(400)
                .body("violations.message[0]", is("size must be between 1 and 100"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        
    }

    @Test
    void testOpprettKontaktIngenEtternavn() {
        try {
            KontaktData data = createKontakt("Mickey", null);            
            String jsonBody = objectMapper.writeValueAsString(data);
            given()
                .header("adressebokId", adressebokId.getValue().toString())
                .header("content-type","application/json")
                .body(jsonBody)
            .when()
                .post("/adressebok/{adressebokid}/kontakt", adressebokId.getValue())
            .then()
                .statusCode(400)
                .body("violations.message[0]", is("must not be null"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        
    }

    @Test
    void testOpprettKontaktTomEtternavn() {
        try {
            KontaktData data = createKontakt("Mickey", "");
            String jsonBody = objectMapper.writeValueAsString(data);
            given()
                .header("adressebokId", adressebokId.getValue().toString())
                .header("content-type","application/json")
                .body(jsonBody)
            .when()
                .post("/adressebok/{adressebokid}/kontakt", adressebokId.getValue())
            .then()
                .statusCode(400)
                .body("violations.message[0]", is("size must be between 1 and 50"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        
    }

    @Test
    void testOpprettKontaktGyldigEpost() {
        try {
            KontaktData data = createKontakt("Mickey", "Mouse");
            data = addEpostToKontakt(data, "mickey.mouse@disney.com");
            String jsonBody = objectMapper.writeValueAsString(data);
            given()
                .header("adressebokId", adressebokId.getValue().toString())
                .header("content-type","application/json")
                .body(jsonBody)
            .when()
                .post("/adressebok/{adressebokid}/kontakt", adressebokId.getValue())
            .then()
                .statusCode(200);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        
    }
    
    @Test
    void testOpprettKontaktUgyldigEpost() {
        try {
            KontaktData data = createKontakt("Mickey", "Mouse");
            data = addEpostToKontakt(data, "mickey.mouse@");
            String jsonBody = objectMapper.writeValueAsString(data);
            given()
                .header("adressebokId", adressebokId.getValue().toString())
                .header("content-type","application/json")
                .body(jsonBody)
            .when()
                .post("/adressebok/{adressebokid}/kontakt", adressebokId.getValue())
            .then()
                .statusCode(400)
                .body("violations.message[0]", is("must be a well-formed email address"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        
    }

    private KontaktData createKontakt(String fornavn, String etternavn) {
        Navn navn = new Navn();
        navn.setFornavn(fornavn);
        navn.setEtternavn(etternavn);
        KontaktData data = new KontaktData();
        data.setNavn(navn);
        return data;
    }

    private KontaktData addEpostToKontakt(KontaktData data, String strEpost) {
        Epost epost = new Epost();
        epost.setEpostadresse(strEpost);        
        data.getEpostadresser().add(epost);
        return data;
    }
}
