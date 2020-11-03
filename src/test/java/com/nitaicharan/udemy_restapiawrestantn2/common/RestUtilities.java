package com.nitaicharan.udemy_restapiawrestantn2.common;

import static org.hamcrest.Matchers.lessThan;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.nitaicharan.udemy_restapiawrestantn2.constants.Auth;
import com.nitaicharan.udemy_restapiawrestantn2.constants.Path;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestUtilities {
    public static String ENDPOINT;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static ResponseSpecBuilder RESPONSE_BUILDER;

    public static void setEndPoint(String endpoint) {
        ENDPOINT = endpoint;
    }

    public static RequestSpecification getRequestSpecification() {
        var authenticationScheme = RestAssured.oauth(//
                Auth.TWITTER_KEY//
                , Auth.TWITTER_SECRET_KEY//
                , Auth.TWITTER_TOKEN//
                , Auth.TWITTER_SECRET_TOKEN//
        );

        REQUEST_BUILDER = new RequestSpecBuilder()//
                .setBaseUri(Path.BASE_URL)//
                .setAuth(authenticationScheme);

        return REQUEST_BUILDER.build();
    }

    public static ResponseSpecification getRespondeSpecification() {

        RESPONSE_BUILDER = new ResponseSpecBuilder()//
                .expectStatusCode(200)//
                .expectResponseTime(lessThan(3L), TimeUnit.SECONDS);//

        return RESPONSE_BUILDER.build();
    }

    public static RequestSpecification createQueryParam(RequestSpecification r, String param, String value) {
        return r.queryParam(param, value);
    }

    public static RequestSpecification createQueryParam(RequestSpecification r, Map<String, String> queryMap) {
        return r.queryParams(queryMap);
    }
}
