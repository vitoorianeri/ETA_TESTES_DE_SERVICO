import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.getUserRequest;
import static requests.UserEndpoint.deleteUserRequest;
import static requests.UserEndpoint.postUserRequest;


public class GETUserTest extends TestBase {

    private User validUser1;
    private User validUser2;

    @BeforeClass
    public void generateTestData(){
        validUser1 = new User("Francisco", "teste443@mail.com","Teste@123", "false");
        postUserRequest(SPEC,validUser1);
        validUser2 = new User("Chico", "teste553@mail.com", "Teste@123", "false");
        postUserRequest(SPEC,validUser2);
    }

    @DataProvider(name="userQueryData")
    public Object[][] createQueryData(){
        return new Object[][]{
                {"nome", validUser1.nome},
                {"email", validUser2.email}
        };
    }

    @Test
    public void shouldReturnUserAndStatuscode200(){
        Response getUserResponse = getUserRequest(SPEC);
        getUserResponse.
                    then().
                            assertThat().
                            statusCode(200).
                            body("quantidade", equalTo(13)).
                            body("quantidade", instanceOf(Integer.class)).
                            body("usuarios", instanceOf(List.class));


    }

    @Test(dataProvider = "userQueryData")
    public void shouldReturnUserForQueryAndStatusCode200(String query, String queryValue) {
        SPEC.queryParam(query, queryValue);
        Response getUserResponse = getUserRequest(SPEC);
        getUserResponse.then().log().all();

        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) SPEC;
        filterableRequestSpecification.removeQueryParam(query);
    }

    @AfterClass
    public void removeTestData(){

        deleteUserRequest(SPEC, validUser1);
        deleteUserRequest(SPEC, validUser2);
    }
}
