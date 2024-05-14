package org.example;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

public class Main {
    public static void main(String[] args) {
//This is the JIRA instance running on our local machine
        RestAssured.baseURI="http://localhost:8080/";
        //When we want to add a comment, we first have to login and grab a session ID
        SessionFilter session=new SessionFilter();
       String response= given().header("content-type", "application/json"). body("{"+
                        "\"username\": \"degenkev\","+
                        "\"password\": \"xxxxxx\","+
                        "}"+
                        "}}").
               //We have to pass our session variable to our filter method for example when one logs in they obtain a session ID
               log().all().filter(session).when().
                post("rest/auth/1/session").then().extract().response().asString();
        given().pathParam("id", "10000").header("content-type", "application/json").
                body("{"+
                        "\"fields\": {"+
                        "\"project\":{"+
                        "\"key\": \"RES\""+
                        "},"+
                        "\"summary\": \"Issue 1 Comment\","+
                        "\"description\": \"I have attached a loom video for refernce\","+
                        "\"issuetype\": {"+
                        "\"name\": \"Bug\""+
                        "}"+
                        "}}").filter(session).when().
                        post("/rest/api/2/issue").then().statusCode(201).extract().response();
                }

    };

//};