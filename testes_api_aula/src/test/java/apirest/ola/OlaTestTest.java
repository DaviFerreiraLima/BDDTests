package apirest.ola;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OlaTestTest {

    private static final Logger logger = LoggerFactory.getLogger(OlaTestTest.class);

    @Test
    void deveObterOlaComSucesso() {
        Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");

        assertEquals(200, response.getStatusCode());
        assertEquals("Ola Mundo!", response.body().asPrettyString());
    }


    @Test
    void deveObterOlaComSucessoHamcrest() {
        Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");

        logger.info(response.body().asPrettyString());
        logger.info(response.headers().toString());
        logger.info(String.valueOf(response.getStatusCode()));

        assertThat(response.body().asString(), is("Ola Mundo!"));
        assertThat(response.statusCode(), is(200));

    }

    @Test
    void deveObterOlaComSucessoBDD() {

                given()
                        .baseUri("http://restapi.wcaquino.me")
                        .port(80)
                .when()
                        .get("/ola")
                .then()
                        .log().all()
                        .body(is("Ola Mundo!"))
                        .statusCode(is(200));
    }
}
