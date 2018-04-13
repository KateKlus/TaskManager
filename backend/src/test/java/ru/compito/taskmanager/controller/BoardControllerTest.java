package ru.compito.taskmanager.controller;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import ru.compito.taskmanager.TaskManagerApplication;
import ru.compito.taskmanager.entity.Board;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = TaskManagerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port = 8080"
)
public class BoardControllerTest {
    private static final String BASE_URL = "http://localhost:8080/api";

    @Test
    public void createBoardTest() throws JSONException {
        Board createdBoard = createBoardRequest();
        assertEquals("TestBoard",createdBoard.getBoardName());
        assertEquals("descriptionTest", createdBoard.getDescription());
        assertEquals("2", createdBoard.getBoardOwner().getId().toString());
        given()
                .when()
                .delete(BASE_URL+"/boards/"+createdBoard.getId()+"/");
    }

    @Test
    public void updateTest() throws JSONException {
        Board createdBoard = createBoardRequest();
        JSONObject editBoard = new JSONObject();
        editBoard.put("id",createdBoard.getId());
        editBoard.put("boardName","EditedBoardName");
        editBoard.put("description","descriptionEdited");
        Board editedBoard = given()
                .contentType(ContentType.JSON)
                .body(editBoard.toString())
                .when()
                .put(BASE_URL+"/boards/"+createdBoard.getId()+"/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(Board.class);
        assertEquals(editBoard.get("boardName"),editedBoard.getBoardName());
        assertEquals(editBoard.get("description"), editedBoard.getDescription());
        assertEquals(createdBoard.getId(), editedBoard.getId());
        given()
                .when()
                .delete(BASE_URL+"/boards/"+createdBoard.getId()+"/");
    }

    @Test
    public void deleteTest() throws JSONException {
        Board createdBoard = createBoardRequest();
        given()
                .when()
                .delete(BASE_URL+"/boards/"+createdBoard.getId()+"/");
        given()
                .when()
                .get(BASE_URL+"/boards/"+createdBoard.getId()+"/")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

    }

    public Board createBoardRequest() throws JSONException {
        JSONObject newBoard = new JSONObject();
        newBoard.put("boardName","TestBoard");
        newBoard.put("description","descriptionTest");
        return given()
                .contentType(ContentType.JSON)
                .body(newBoard.toString())
                .when()
                .post(BASE_URL+"/users/2/boards/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(Board.class);
    }

}