package com.application.testcases;

import com.application.clients.BaseTest;
import com.application.clients.UserClient;
import com.github.javafaker.Faker;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserTestCases extends BaseTest {
    UserClient user = new UserClient();
    Faker faker = new Faker();

    @DataProvider(name = "user_e2e_success")
    public Object[][] dpRegisterSucces() {
        return new Object[][]{{"Name", ""+faker.name().firstName()+"@hotmail.com", "password", false}};
    }

    @DataProvider(name = "user_register_failure")
    public Object[][] dpRegisterFailure() {
        return new Object[][]{
                {"", "email@hotmail.com", "password", false},
                {"Name", "", "password", false},
                {"Name", "email@hotmail.com", "", false}};
    }

    @DataProvider(name = "user_get_failure")
    public Object[][] dpGetFailure() {
        return new Object[][]{{faker.name().toString()}, {faker.number().toString()}};
    }

//tests
    @Test (dataProvider = "user_e2e_success")
    public void testUserRegisterSucces(String name, String email, String pass, Boolean adminTrue){
        Response postResponse;
        Response getResponse;
        Response deleteResponse;

        postResponse = user.createUser(name,email,pass,adminTrue);
        postResponse.then().statusCode(201);
        String userId = postResponse.jsonPath().get("_id");

        getResponse = user.getUser(userId);
        getResponse.then().statusCode(200);

        deleteResponse = user.deleteUser(userId);
        deleteResponse.then().statusCode(200);
        System.out.println(deleteResponse.jsonPath().get("").toString());

    }

    @Test (dataProvider = "user_e2e_success")
    public void testUserPutSucces(String name, String email, String pass, Boolean adminTrue){
        Response postResponse;
        Response getResponse;
        Response putResponse;

        postResponse = user.createUser(name,email,pass,adminTrue);
        postResponse.then().statusCode(201);
        String userId = postResponse.jsonPath().get("_id");

        getResponse = user.getUser(userId);
        getResponse.then().statusCode(200);

        String newName = faker.name().firstName().toString();
        putResponse = user.updateUser(userId,newName,email,pass,adminTrue);
        System.out.println(putResponse.jsonPath().get("").toString());

        getResponse = user.getUser(userId);
        getResponse.then().statusCode(200);

    }

    @Test (dataProvider = "user_register_failure")
    public void testUserRegisterFailure(String name, String email, String pass, Boolean adminTrue){
        Response postResponse;
        postResponse = user.createUser(name,email,pass,adminTrue);
        postResponse.then().statusCode(400);
    }

    @Test (dataProvider = "user_get_failure")
    public void testUserGetFailure(String userId){
        Response getResponse;
        getResponse = user.getUser(userId);
        getResponse.then().statusCode(400);
    }
}

