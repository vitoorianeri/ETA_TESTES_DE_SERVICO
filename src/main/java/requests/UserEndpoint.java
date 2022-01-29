package requests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;


public class UserEndpoint extends RequestBase{
    public String batata = "batata";

    public static Response getUserRequest(RequestSpecification spec){
        Response getUserResponse =
                given().
                        spec(spec).
                when().
                        get("/usuarios");
        return getUserResponse;
    }

    public static Response postUserRequest(RequestSpecification spec, User user){

        Gson gson = new Gson();
        String userJsonRepresentation = gson.toJson(user);
//        String userJsonRepresentation = "{\n" +
//                "  \"nome\": \"Fulano da Silva\",\n" +
//                "  \"email\": \"beltrano123@qa.com.br\",\n" +
//                "  \"password\": \"teste\",\n" +
//                "  \"administrador\": \"true\"\n" +
//                "}";

        Response postUserResponse =
                given().
                        baseUri("http://localhost:3000").
                        header("Content-Type", "application/json").
                        header("accept","application/json").
                        and().
                        body(userJsonRepresentation).
                        log().all().
                when().
                        post("/usuarios");

        user.setUserId(getValueFromResponse(postUserResponse, "_id"));
        return postUserResponse;

    }

    public static Response deleteUserRequest(RequestSpecification spec, User user){

        Response deleteUserResponse =
                given().
                        spec(spec).
                        pathParam("_id", user._id).
                when().
                        delete("/usuarios/{_id}");

        return deleteUserResponse;
    }

}
