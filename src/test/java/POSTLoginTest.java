import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import models.User;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.deleteUserRequest;
import static requests.UserEndpoint.postUserRequest;
import static requests.LoginEndpoints.postLoginRequest;

public class POSTLoginTest extends TestBase{

    private User validUser1;

    @BeforeClass
    public void generateTestData() {
        validUser1 = new User("ANA", "ana1112356@gmail.com", "1234@A", "false");
        postUserRequest(SPEC, validUser1);
    }

    @Test
    public void shouldReturnSuccessMessageAndStatusCode200() {
        Response loginUserResponse = postLoginRequest(SPEC, validUser1);
        loginUserResponse.
                then().
                        assertThat().
                        statusCode(200).
                        body("message", equalTo("Login realizado com sucesso")).
                        body("authorization", notNullValue());
    }

    @AfterClass
    public void removeTestData() {
        deleteUserRequest(SPEC, validUser1);
    }
}
