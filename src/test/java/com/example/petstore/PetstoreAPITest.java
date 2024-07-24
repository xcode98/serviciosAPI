package com.example.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetstoreAPITest {

    @Test
    public void testFindPetsByStatus() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        Response response = given()
            .header("api_key", "special-key")
            .queryParam("status", "available")
            .when()
            .get("/pet/findByStatus")
            .then()
            .statusCode(200)
            .body("status", everyItem(equalTo("available")))
            .extract().response();

        System.out.println(response.asString());
    }

    @Test
    public void testAddPet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String requestBody = """
        {
            "id": 0,
            "category": {
                "id": 0,
                "name": "string"
            },
            "name": "doggie",
            "photoUrls": [
                "string"
            ],
            "tags": [
                {
                    "id": 0,
                    "name": "string"
                }
            ],
            "status": "available"
        }
        """;

        Response response = given()
            .header("api_key", "special-key")
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/pet")
            .then()
            .statusCode(200)
            .body("name", equalTo("doggie"))
            .extract().response();

        System.out.println(response.asString());
    }

    @Test
    public void testUpdatePet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        long petId = 9223372016900040000L; // Usar long con sufijo L
        String updatedName = "esmele";
        String updatedStatus = "sold";

        Response response = given()
            .header("api_key", "special-key")
            .contentType(ContentType.URLENC) // Cambiado a application/x-www-form-urlencoded
            .formParam("name", updatedName) // Usar formParam para parámetros form-data
            .formParam("status", updatedStatus)
            .when()
            .post("/pet/" + petId)
            .then()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("message", equalTo(String.valueOf(petId))) // Validar mensaje
            .extract().response();

        System.out.println(response.asString());
    }

    @Test
    public void testDeletePet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        long petId = 9727313L; // Usar long con sufijo L

        Response response = given()
            .header("api_key", "special-key")
            .when()
            .delete("/pet/" + petId)
            .then()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("message", equalTo(String.valueOf(petId))) // Validar mensaje
            .extract().response();

        System.out.println(response.asString());
    }

}
