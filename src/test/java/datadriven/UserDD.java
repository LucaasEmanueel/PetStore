package datadriven;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UserDD {

    Data data;

    String uri = "https://petstore.swagger.io/v2/user";

    @BeforeMethod
    public void setUp(){
        data = new Data();
    }

    @Test
    public void createUser() throws IOException {

        String json = data.lerJson("db/user1.json");
        String idUser =
                given()
                        .contentType("application/json")
                        .log().all()
                        .body(json)
                .when()
                        .post(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("code", is(200))
                        .body("type", is("unknown"))
                .extract()
                        .path("message");

        System.out.println("O userID é: " + idUser);
    }
}
