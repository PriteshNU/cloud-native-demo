package com.cloudnative.webapi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAPIIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String testUserName;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @BeforeAll
    public static void init() {
        LocalDateTime now = LocalDateTime.now();
        String datetimeStr = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        testUserName = String.format("user_%s@email.com", datetimeStr);
    }

    @Test
    @Order(1)
    public void testCreateAndValidateUser() {
        String username = testUserName;
        String password = "Password@123";

        JSONObject newUser = new JSONObject();
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("first_name", "TestFirstname");
        newUser.put("last_name", "TestLastname");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(newUser.toJSONString())
                .when()
                .post("/v1/user")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("username", equalTo(username))
                .extract()
                .response();

        String id = response.jsonPath().getString("id");
        assertDoesNotThrow(() -> UUID.fromString(id), "Invalid UUID");

        verifyTestUserAccount(username);

        getUserAndVerify(username, password)
                .body("id", notNullValue())
                .body("id", equalTo(id))
                .body("username", equalTo(username));
    }

    @Test
    @Order(2)
    public void testUpdateAndValidateUser() {
        String username = testUserName;
        String currentPassword = "Password@123";
        String updatedPassword = "NewPassword@123";
        String updatedFirstname = "TestNewFirstname";

        JSONObject newUser = new JSONObject();
        newUser.put("password", updatedPassword);
        newUser.put("first_name", updatedFirstname);

        given()
                .auth()
                .basic(username, currentPassword)
                .contentType(ContentType.JSON)
                .body(newUser.toJSONString())
                .when()
                .put("/v1/user/self")
                .then()
                .statusCode(204);

        getUserAndVerify(username, updatedPassword)
                .body("id", notNullValue())
                .body("username", equalTo(username))
                .body("first_name", equalTo(updatedFirstname))
                .body("last_name", equalTo("TestLastname"));
    }

    private ValidatableResponse getUserAndVerify(String username, String password) {
        return given()
                .auth()
                .basic(username, password)
                .when()
                .get("/v1/user/self")
                .then()
                .statusCode(200);
    }

    private void verifyTestUserAccount(String username) {
        String sql = "UPDATE users SET account_verified = true WHERE username = ?";
        jdbcTemplate.update(sql, username);
    }
}
