package com.fifa.worldcup.integration;

import com.fifa.worldcup.domain.Player;
import com.fifa.worldcup.repository.PlayerRepository;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerRestControllerIntegrationTest {

    private RequestSpecification specification;

    @LocalServerPort
    private Integer port;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    private void beforeEach() {
        specification = new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                setBaseUri("http://localhost:" + port).
                build();
    }

    @AfterEach
    public void afterEach() {
        playerRepository.deleteAll();
    }

    @Test
    public void test() {
        Player player = new Player("Juan", "Pedro");
        playerRepository.save(player);
        List<Player> players = Arrays.asList(given().
                spec(specification).
                get("/players").
                then().
                statusCode(200).
                extract().
                as(Player[].class));

        Assertions.assertThat(players).hasSize(1);
    }
}
