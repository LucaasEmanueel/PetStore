package petstore;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Store {

    Data data;
    String uri = "https://petstore.swagger.io/v2/store";

    @BeforeMethod
    public void setUp(){
        data = new Data();
    }

    @Test
    public void inserirStore() throws IOException {
        String jsonStore = data.lerJson("db/store1.json");

        given()
                .contentType("application.json")
                .log().all()
                .body(jsonStore)
        .when()
                .post(uri + "/" + "order")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is("20041996"))
                .body("shipDate", is("05-09-2021"));
    }


}
