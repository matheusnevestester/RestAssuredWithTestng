package com.application.clients;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserClient {


    public Response createUser(String name, String email, String pass, Boolean adminTrue) {

        JSONObject request = new JSONObject();
        request.put("nome", name);
        request.put("email", email);
        request.put("password", pass);
        request.put("administrador", adminTrue.toString());

        System.out.println(request);
        System.out.println(request.toString());

        return RestAssured.given().contentType("application/json").
                body(request.toString()).
                when().
                post("/usuarios");

    }

    public Response getUser(String userId) {

        return RestAssured.when().get("/usuarios/" + userId + "");

    }

    public Response updateUser(String userId, String name, String email, String pass, Boolean adminTrue) {
        JSONObject request = new JSONObject();
        request.put("nome", name);
        request.put("email", email);
        request.put("password", pass);
        request.put("administrador", adminTrue.toString());

        return RestAssured.given().contentType("application/json").
                body(request.toString()).when().
                put("/usuarios/" + userId + "");
    }

    public Response deleteUser(String userId){

        return RestAssured.when().delete("/usuarios/" + userId + "");

    }

    public void validateGetUserInfo() {

    }
}
