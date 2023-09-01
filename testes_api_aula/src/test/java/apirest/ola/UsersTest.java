package apirest.ola;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class UsersTest {

    @Test
    void deveObterUsuariosComSucesso(){
        given()
                .baseUri("http://restapi.wcaquino.me")
                .port(80)
        .when()
                .get("/users")

        .then()
                .log().all()
                .statusCode(200)
                .body("",hasSize(3))
                .body("age.findAll{it <= 25}", hasSize(2))
                .body("age.findAll{it > 25 && it <= 30}", hasSize(1))
                .body("findAll{it.age <= 25}.name",hasItem("Ana Júlia"))
                .body("name.collect{it.toUpperCase()}",hasItem("ANA JÚLIA"))
                .body("salary.max()",is(2500))
                .body("salary.max()", allOf(greaterThan(2000),lessThan(3000)))
                    ;

    }

    @Test

    void deveObterUsuarioComSucesso(){

        given()
                .baseUri("http://restapi.wcaquino.me")
                .port(80)
        .when()
                .get("/users/1")
        .then()
                .log().all()
                .statusCode(200)
                .body("id",is(1))
                .body("name",containsString("Silva"))
                .body("age",greaterThan(18))
                .body("salary",is(1234.5678));


    }

    @Test

    void deveValidarObjetosAninhadosComSuceso(){

        given()
                .baseUri("http://restapi.wcaquino.me")
                .port(80)
        .when()
                .get("/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("id",is(2))
                .body("endereco.rua",is("Rua dos bobos"))
                .body("edereco.numero",is(0));

    }

    @Test
    void deveValidarArrayComSucessc() {

        given()
                .baseUri("http://restapi.wcaquino.me")
                .port(80)
                .when()
                .get("/users/3")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(3))
                .body("filhos", hasSize(2))
                .body("filhos[0].name", is("Zezinho"))
                .body("filhos.name", hasItem("Luizinho"))
                .body("filhos.name", hasItems("Luizinho", "Zezinho"))
                .body("filhos.name.toArray()", arrayContaining("Zezinho", "Luizinho"))
        ;
    }


        @Test
        public void deveObterUsuariosComSucesso2(){

            ArrayList<String> nomes =
           given()
                    .baseUri("http://restapi.wcaquino.me")
                    .port(80)
            .when()
                    .get("/users")

            .then()
                    .log().all()
                    .statusCode(200)
                    .body("", hasSize(3))
                    .extract().path("name");


            assertThat(nomes.toArray(),arrayContaining("João da Silva", "Maria Joaquina","Ana Júlia"));

        }


    }

