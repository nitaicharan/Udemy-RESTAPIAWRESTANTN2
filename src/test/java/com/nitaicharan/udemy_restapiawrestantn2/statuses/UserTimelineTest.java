package com.nitaicharan.udemy_restapiawrestantn2.statuses;

import static org.hamcrest.Matchers.hasItem;

import com.nitaicharan.udemy_restapiawrestantn2.common.RestUtilities;
import com.nitaicharan.udemy_restapiawrestantn2.constants.Auth;
import com.nitaicharan.udemy_restapiawrestantn2.constants.EndPoints;
import com.nitaicharan.udemy_restapiawrestantn2.constants.Path;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.specification.RequestSpecification;

public class UserTimelineTest {

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

        this.tweetId = response.path("id_str");
    }

    @Test(dependsOnMethods = { "postTweet" })
    public void readTweets() {
        requestSpecification.queryParam("user_id", Auth.TWITTER_USER_ID);

        RestUtilities.getRespondeSpecification();
        RestUtilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);

        RestUtilities.getResponse(//
                RestUtilities.createQueryParam(requestSpecification, "count", "1")//
                , "get"//
                , false//
        ).then().body("user.screen_name", hasItem(Auth.TWITTER_USER_ID));
    }

    @Test(dependsOnMethods = { "readTweets" })
    public void deleteTweet() {
        RestUtilities.getRespondeSpecification();
        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_DESTROY);

        RestUtilities.getResponse(//
                RestUtilities.createQueryParam(requestSpecification, "id", this.tweetId)//
                , "post"//
                , false//
        );
    }
}
