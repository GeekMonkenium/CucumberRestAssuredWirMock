package com.countries;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import Util.Utilities;
import org.apache.log4j.Logger;
import org.junit.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountriesStepDef{

    private Response response;
    private RequestSpecification request;
    private static final Logger LOGGER = Logger.getLogger(CountriesStepDef.class);

    @Rule
    WireMockServer wireMockServer = new WireMockServer(8089);


    @Before("@Smoke")
    public void setupStub() {
        LOGGER.info("API : Setting up Stub");
        if(!wireMockServer.isRunning()) {
            wireMockServer.resetAll();
            wireMockServer.start();

            WireMock.configureFor("localhost", 8089);
            wireMockServer.stubFor(get(urlEqualTo("/rest/v1/all"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withStatus(200)
                            .withBody(Utilities.getResponseBody("./src/test/resources/response.json"))));
        }
    }

    @Given("^the countries endpoint is configured$")
    public void endpoint_configured() throws Throwable {
        LOGGER.info("API : Configuring Endpoints");
        RestAssured.baseURI = System.getProperty("service.base.url");
        request = given();
    }

    @When("^the user calls Countries web service$")
    public void the_user_calls_countries_web_service() throws Throwable {
        LOGGER.info("API : User Calls Countries Web Services");
        response = request.when().get("/all");
        System.out.println("response: " + response.prettyPrint());
    }

    @Then("^User gets success response$")
    public void user_gets_success_response() throws Throwable {
        LOGGER.info("API : User Calls Countries Web Services");
        get("/all").then().assertThat().body("name", hasItem("Afghanistan"));
    }

    @Then("^the service returns HTTP (\\d+)$")
    public void theServiceReturnsHTTP(int statusCode) throws Throwable {
        LOGGER.info("API : Validating Status Code for : " + statusCode);
        assertEquals("Invalid status code",response.getStatusCode(),statusCode);    }

    @After
    public void stopWireMock(){

        wireMockServer.stop();
    }

    @Then("^the countries service responds in (\\d+) seconds$")
    public void theCountriesServiceRespondsInSeconds(int responseTime) throws Throwable {

        LOGGER.info("API : Validating service returns results within : " + responseTime);
        // IMP : response time measurement should be performed when the JVM is hot!
        // (i.e. running a response time measurement when only running a single test
        // will yield erroneous results)
        when().
                get("/all").
                then().
                time(lessThan(Long.valueOf(responseTime*1000)));

    }
}