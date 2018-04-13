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
import ru.compito.taskmanager.entity.User;

import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = TaskManagerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port = 8080"
)
public class UserControllerTest {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final String USER_EXIST="Error this username already exists";
    private static final String BAD_LOGIN="No special characters are allowed in the username";

    @Test
    public void getUserByIdTest() {
        List<User> userList = new LinkedList<>();
        User user1 = new User();
        user1.setUsername("admin");
        user1.setEmail("admin@gmail.com");
        user1.setId(1);
        userList.add(user1);
        User user2 = new User();
        user2.setUsername("NoriX");
        user2.setEmail("versatility.95@mail.ru");
        user2.setId(2);
        userList.add(user2);
        User user3 = new User();
        user3.setUsername("user");
        user3.setEmail("user@mail.ru");
        user3.setId(3);
        userList.add(user3);
        for(int i=1; i<4; i++){
            User receivedUser = given()
                    .when()
                        .get(BASE_URL+"/users/"+userList.get(i-1).getId()+"/")
                    .then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .body().as(User.class);
            assertEquals(userList.get(i-1).getId(), receivedUser.getId());
            assertEquals(userList.get(i-1).getUsername(), receivedUser.getUsername());
            assertEquals(userList.get(i-1).getEmail(), receivedUser.getEmail());
        }
    }
    @Test
    public void registerTest() throws JSONException {
        Boolean result = false;
        String answer;
        List userList;
        JSONObject newUser = new JSONObject();
        newUser.put("username", "TestMe");
        newUser.put("fullname", "TestMe");
        newUser.put("password", "PassworD");
        newUser.put("passwordConfirmation", "PassworD");
        newUser.put("email", "TestMe@mail.ru");
        answer = given()
                .contentType(ContentType.JSON)
                .body(newUser.toString())
                .when()
                .post(BASE_URL + "/register/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body().asString();
        assertEquals(USER_EXIST, answer);
         userList = given()
                .when()
                .get(BASE_URL + "/users/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(List.class);
        for (Object user : userList.toArray()) {
            if (user.toString().contains(newUser.get("username").toString())) {
                result = true;
            }
        }
        assertTrue(result);
        newUser.put("username", "Te234#$!@#stMe");
        answer = given()
                .contentType(ContentType.JSON)
                .body(newUser.toString())
                .when()
                .post(BASE_URL + "/register/")
                .then()
                .statusCode(HttpStatus.OK.value()).extract().body().asString();
        assertEquals(BAD_LOGIN, answer);
    }
}