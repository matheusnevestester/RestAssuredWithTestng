package com.application.clients;

import io.restassured.RestAssured;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

@Test
public class BaseTest {

    @BeforeSuite
    public void seeUpTest() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("./src/test/java/com/properties/server_infomation.yml"));
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        RestAssured.baseURI = data.get("host").toString();
    }
}
