import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static requests.LoginEndpoints.postLoginRequest;
import static requests.ProductEndpoint.postProductRequest;
import static requests.UserEndpoint.postUserRequest;
import static requests.UserEndpoint.deleteUserRequest;
import static requests.ProductEndpoint.deleteProductRequest;

public class POSTProductTest extends TestBase{

    private Product product1;
    private User validUser3;

    @BeforeClass
    public void generateTestData() {
        validUser3 = new User("ANA", "ana1112356@gmail.com", "1234@A", "false");
        postUserRequest(SPEC, validUser3);
        postLoginRequest(SPEC, validUser3);
        product1 = new Product("Celular 4", 1350, "portátil", 5);
        postProductRequest(SPEC, product1);
    }

    @Test
    public void shouldReturnSuccessMessageAndStatusCode201() {
        SPEC.header("Authorization", validUser3.getToken_autorization());
        Response postProductResponse = postProductRequest(SPEC, product1);
        postProductResponse.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo("Cadastro realizado com sucesso")).
                body("_id", notNullValue());
    }

    @AfterClass
    public void removeTestData(){
        deleteProductRequest(SPEC, product1, validUser3);
        deleteUserRequest(SPEC, validUser3);
    }
}
