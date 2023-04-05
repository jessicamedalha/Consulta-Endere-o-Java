package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.net.URI;
import java.net.URISyntaxException;

public class ConsultaCep {

    private Scenario scenario;
    private Response response;
    private final String BASE_URL = "http://localhost:8888";

    @Before
    public void before(Scenario scenarioVal) {
        this.scenario = scenarioVal;
    }
    @Given("Get Call to {string}")
    public void getCallTo(String url) throws URISyntaxException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification req = RestAssured.given();
        response = req.when().get(new URI(url));
    }

    @Then("Response Code {string} is validated")
    public void responseCodeIsValidated(String resp) {
        int responseCode = response.then().extract().statusCode();
        Assertions.assertEquals(resp, responseCode+"");
    }
}
