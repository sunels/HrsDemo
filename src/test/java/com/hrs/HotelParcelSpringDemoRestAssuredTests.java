package com.hrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.model.GuestModel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@ActiveProfiles (value = "test")
class HotelParcelSpringDemoRestAssuredTests {

    @Before
    public void setup() {
/*
        RestAssured.baseURI = "http://localhost:8080/";
*/
    }

    @Test
    public void whenAddGuest_thenSuccess() throws JsonProcessingException {
/*        GuestModel guest = new GuestModel();
        guest.setFullName("serkan sunel");
        guest.setEmail("serkan.sunel@gmail.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(guest);

        given()
                .contentType(ContentType.JSON) // Set content type to JSON
                .body(requestBody) // Set the JSON request body
                .when()
                .request("POST", "/guest")
                .then()
                .statusCode(200);*/
    }

}
