package Endpoints;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import Util.Utilities;
import org.junit.ClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class MockBase {

    @ClassRule
    public static WireMockRule wireMock = new WireMockRule( 8089 );

    public MockBase() {
        setupStub();
    }

    public static void setupStub() {

        stubFor( get( urlEqualTo( "/rest/v1/all" ) )
                .willReturn( aResponse()
                        .withHeader( "Content-Type", "application/json" )
                        .withStatus( 200 )
                        .withBody( Utilities.getResponseBody("./src/test/resources/response.json")) ) );

    }

    public static void getServiceResponse(){

        System.out.println(wireMock.isRunning());
        given().
                when()
                .get("http://localhost:8089/rest/v1/all")
                .then()
                .statusCode(200);

    }
}
