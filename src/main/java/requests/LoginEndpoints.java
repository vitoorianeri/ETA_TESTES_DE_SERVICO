package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;

public class LoginEndpoints extends RequestBase{
    public static Response postLoginRequest(RequestSpecification spec, User user){

        Response postLoginResponse =
                given().
                        spec(spec).
                        header("Content-Type", "application/json").
                        and().
                        body(user.getUserCredentials()).
                        log().all().
                when().
                        post("/login");

        user.setToken_autorization(getValueFromResponse(postLoginResponse, "authorization"));
        return postLoginResponse;
    }
}
