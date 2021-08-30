package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

import static io.restassured.RestAssured.given;

public class Pet {
        String uri = "https://petstore.swagger.io/v2/pet";

        public String lerJson(String caminhoJson) throws IOException {
                return new String(Files.readAllBytes(Paths.get(caminhoJson)));
        }

        @Test (priority = 2)
        public void incluirPet() throws IOException {
                String jsonBody = lerJson("db/pet1.json");

                given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)
                .when()
                        .post(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is("Atena"))
                        .body("status", is("Para vender"))
                        .body("category.name", is("AFVMRO12038LD"))
                        .body("tags.name", contains("data"));
        }

        @Test (priority = 1)
        public void consultarPet(){
                String petId = "2004199649";

                String token  =
                given()
                        .contentType("application/json")
                        .log().all()
                .when()
                        .get(uri + "/" + petId)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is ("Atena"))
                        .body("category.name", is("AFVMRO12038LD"))
                        .body("status", is("Para vender"))
                        .body("tags.id", contains(20213008))
                        .extract()
                                .path("category.name")
                ;

                System.out.println("O token é " + token);

        }
}
