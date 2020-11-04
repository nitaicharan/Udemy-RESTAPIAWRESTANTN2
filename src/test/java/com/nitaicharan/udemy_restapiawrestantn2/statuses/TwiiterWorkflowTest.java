package com.nitaicharan.udemy_restapiawrestantn2.statuses;

import static org.hamcrest.Matchers.equalTo;

import com.nitaicharan.udemy_restapiawrestantn2.common.RestUtilities;
import com.nitaicharan.udemy_restapiawrestantn2.constants.EndPoints;
import com.nitaicharan.udemy_restapiawrestantn2.constants.Path;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.specification.RequestSpecification;

public class TwiiterWorkflowTest {
    private String tweetId;
    private RequestSpecification requestSpecification;

    @BeforeClass
    public void setup() {
        requestSpecification = RestUtilities.getRequestSpecification();
        requestSpecification.basePath(Path.STATUSES);
    }

    @Test
    public void postTweet() {
        RestUtilities.getRespondeSpecification();
        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_POST);

        var response = RestUtilities.getResponse(//
                RestUtilities.createQueryParam(requestSpecification, "status", "My First Tweet #first")//
                , "post"//
                , false//
        );

        tweetId = response.path("id_str");
    }

    @Test(dependsOnMethods = { "postTweet" })
    public void readTweets() {
        RestUtilities.getRespondeSpecification();
        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_READ_SINGLE);

        RestUtilities.getResponse(//
                RestUtilities.createQueryParam(requestSpecification, "id", tweetId)//
                , "get"//
                , false//
        ).then().body("id_str", equalTo(tweetId));
    }

    @Test(dependsOnMethods = { "readTweets" })
    public void deleteTweet() {
        RestUtilities.getRespondeSpecification();
        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_DESTROY_PATH);

        RestUtilities.getResponse(//
                RestUtilities.createPathParam(requestSpecification, "id", tweetId)//
                , "post"//
                , false//
        );
    }

}
