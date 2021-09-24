package petstore;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class Pet {

        Data data;
        String uri = "https://petstore.swagger.io/v2/pet";

        @BeforeMethod
        public void setUp(){
                data = new Data()
;        }

        @Test (priority = 1)
        public void incluirPet() throws IOException {
                String jsonBody = data.lerJson("db/pet1.json");

                given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)
                .when()
                        .post(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is("DogPB"))
                        .body("status", is("available"))
                        .body("category.name", is("AFVMRO12038LD"))
                        .body("tags.name", contains("data"));
        }

        @Test (priority = 2)
        public void consultarPet(){
                String petId = "200419964";

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

        @Test (priority = 3)
        public void alterarPet() throws IOException {
                String jsonBody = data.lerJson("db/pet2.json");
                given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)
                .when()
                        .put(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is("Atena"))
                        .body("status", is("Vendido"))
                ;
        }

        @Test (priority =  4)
        public void deletarPet(){
                String petId = "2004199649";

                given()
                        .contentType("application/json")
                        .log().all()
                .when()
                        .delete(uri + "/" + petId)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("code", is(200))
                        .body("type", is ("unknown"))
                        .body("message", is(petId));
        }

        @Test
        public void consultarPetPorStatus(){

                String status = "available";

                given()
                        .contentType("application/json")
                        .log().all()
                .when()
                        .get(uri + "/findByStatus?status=" + status)
                .then()
                        .log().all()
                        .statusCode(200)
                .body("name[]", everyItem(equalTo("DogPB")));

        }
}
