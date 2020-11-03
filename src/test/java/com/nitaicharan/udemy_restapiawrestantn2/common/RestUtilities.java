package com.nitaicharan.udemy_restapiawrestantn2.common;

import static org.hamcrest.Matchers.lessThan;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.nitaicharan.udemy_restapiawrestantn2.constants.Auth;
import com.nitaicharan.udemy_restapiawrestantn2.constants.Path;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestUtilities {
    public static String ENDPOINT;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPECIFICATION;

    public static ResponseSpecBuilder RESPONSE_BUILDER;
    public static ResponseSpecification RESPONSE_SPECIFICATION;

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

        REQUEST_SPECIFICATION = REQUEST_BUILDER.build();
        return REQUEST_SPECIFICATION;
    }

    public static ResponseSpecification getRespondeSpecification() {

        RESPONSE_BUILDER = new ResponseSpecBuilder()//
                .expectStatusCode(200)//
                .expectResponseTime(lessThan(3L), TimeUnit.SECONDS);//

        RESPONSE_SPECIFICATION = RESPONSE_BUILDER.build();
        return RESPONSE_SPECIFICATION;
    }

    public static RequestSpecification createQueryParam(RequestSpecification r, String param, String value) {
        return r.queryParam(param, value);
    }

    public static RequestSpecification createQueryParam(RequestSpecification r, Map<String, String> queryMap) {
        return r.queryParams(queryMap);
    }

    public static RequestSpecification createPathParam(RequestSpecification r, String param, String value) {
        return r.pathParam(param, value);
    }

    public static Response getResponse() {
        return RestAssured.given().get(ENDPOINT);
    }

    public static Response getResponse(RequestSpecification r, String type, boolean hasToLog) {
        REQUEST_SPECIFICATION.spec(r);

        Response response = null;
        if ("get".equals(type)) {
            response = RestAssured.given().spec(REQUEST_SPECIFICATION).get(ENDPOINT);
        }

        else if ("post".equals(type)) {
            response = RestAssured.given().spec(REQUEST_SPECIFICATION).post(ENDPOINT);
        }

        else if ("put".equals(type)) {
            response = RestAssured.given().spec(REQUEST_SPECIFICATION).put(ENDPOINT);
        }

        else if ("delete".equals(type)) {
            response = RestAssured.given().spec(REQUEST_SPECIFICATION).delete(ENDPOINT);
        }

        else {
            System.out.println("Type is not suported");
        }

        if (hasToLog) {
            response.then().log().all();
        }

        response.then().spec(RESPONSE_SPECIFICATION);
        return response;
    }

    public static JsonPath getJsonPath(Response res) {
        return new JsonPath(res.asString());
    }

    public static XmlPath getXmlPath(Response res) {
        return new XmlPath(res.asString());
    }

    public static void resetBasePath() {
        RestAssured.basePath = null;
    }

    public static void setContentType(ContentType type) {
        RestAssured.basePath = null;
    }

}
