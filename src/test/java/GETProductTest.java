import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import models.Product;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static requests.ProductEndpoint.getProductRequest;
import static requests.ProductEndpoint.getByIdRequest;

public class GETProductTest extends TestBase {

    private Product product1;

    @BeforeClass
    public void generateTestData(){
        product1 = new Product("12345");
    }

    @Test
    public void shouldReturnProductAndStatuscode200(){
        Response getProductResponse = getProductRequest(SPEC);
        getProductResponse.
                        then().
                                assertThat().
                                statusCode(200).
                                body("quantidade", equalTo(2)).
                                body("quantidade", instanceOf(Integer.class)).
                                body("produtos", instanceOf(List.class));


    }

    @Test
    public void shouldReturnIDAndStatuscode400(){
        Response getProductResponse = getByIdRequest(SPEC, product1);
        getProductResponse.
                        then().
                                assertThat().
                                statusCode(400).
                                body("message", equalTo("Produto não encontrado"));



    }
}
