package requests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import models.Product;
import models.User;

import static io.restassured.RestAssured.given;

public class ProductEndpoint extends RequestBase{

    public static Response getProductRequest(RequestSpecification spec){
        Response getProductResponse =
                given().
                        spec(spec).
                        when().
                        get("/produtos");
        return getProductResponse;
    }

    public static Response getByIdRequest(RequestSpecification spec, Product product){
        Response getByIdRequest =
                given().
                        spec(spec).
                        pathParam("_id", product._id).
                when().
                        get("/produtos/{_id}");
        return getByIdRequest;
    }

    public static Response postProductRequest(RequestSpecification spec, Product product){

        Gson gson = new Gson();
        String productJsonRepresentation = gson.toJson(product);

        Response postProductResponse =
                given().
                        baseUri("http://localhost:3000").
                        header("Content-Type", "application/json").
                        and().
                        body(productJsonRepresentation).
                        log().all().
                        when().
                        post("/produtos");

        product.setProductId((getValueFromResponse(postProductResponse, "_id")));
        return postProductResponse;

    }

    public static Response deleteProductRequest(RequestSpecification spec, Product product, User user){
        spec.header("Authorization", user.getToken_autorization());

        Response deleteProdutoResponse =
                given().
                        spec(spec).
                        pathParam("id_", product._id).
                        when().
                        delete("/produtos/{id_}");

        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) spec;
        filterableRequestSpecification.removeHeader("Authorization");

        return deleteProdutoResponse;
    }
}
