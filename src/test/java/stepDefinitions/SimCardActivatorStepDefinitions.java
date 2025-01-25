package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private String active;

    @Given("I fetch the POST \\/activate endpoint of the activation service with {long}")
    public void i_fetch_the_post_activate_endpoint_of_the_activation_service_with( Long ICCID ) {

        Map<String, Object> payload = Map.of(
                "iccid", ICCID,
                "customerEmail", "customerEmail"
        );

        String uri = "http://localhost:8080/activate";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> activateRequest = new HttpEntity<>(payload, headers);

        restTemplate.postForObject( uri, activateRequest, Map.class );
    }

    @When("I fetch the GET \\/customer endpoint of the activation service with {long}")
    public void i_fetch_the_get_customer_endpoint_of_the_activation_service_with( Long simCardId ) {

        String uri = "http://localhost:8080/customer?simCardId=" + simCardId;

        Map customerResponse = restTemplate.getForObject( uri, Map.class );

        active = customerResponse.get("active").toString();
    }

    @Then("It should respond with {string}")
    public void it_should_respond_with( String expectedActive ) {
        assertEquals(expectedActive, active);
    }

}