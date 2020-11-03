package com.nitaicharan.udemy_restapiawrestantn2.statuses;

import static org.hamcrest.Matchers.hasItem;

import com.nitaicharan.udemy_restapiawrestantn2.common.RestUtilities;
import com.nitaicharan.udemy_restapiawrestantn2.constants.Auth;
import com.nitaicharan.udemy_restapiawrestantn2.constants.EndPoints;
import com.nitaicharan.udemy_restapiawrestantn2.constants.Path;

import org.junit.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserTimelineTest {

    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup() {
        requestSpecification = RestUtilities.getRequestSpecification();
        requestSpecification.queryParam("user_id", "apiautomation");
        requestSpecification.basePath(Path.STATUSES);

        responseSpecification = RestUtilities.getRespondeSpecification();
    }

    @Test
    public void readTweets1() {
        RestAssured.given()//
                .spec(requestSpecification)//
                .when()//
                .get(EndPoints.STATUSES_USER_TIMELINE)//
                .then()//
                .spec(responseSpecification)//
                .body("user.screen_name", hasItem(Auth.TWITTER_USER_ID));
    }

}
