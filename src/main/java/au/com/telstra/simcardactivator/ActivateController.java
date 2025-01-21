package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ActivateController {

    private final ActuateFetchController actuateFetchController;

    String actuateServiceUri = "http://localhost:8444/actuate";

    @Autowired
    public ActivateController(ActuateFetchController actuateFetchController) {
        this.actuateFetchController = actuateFetchController;
    }

    @PostMapping("/activate")
    public Map<String, Object> requestController( @RequestBody Map<String, Object> body ) {
        Map<String, Object> bodyToActuate = Map.of( "iccid", body.get("iccid") );

        System.out.println("Post request body from a client: \t" + body);
        System.out.println("Request bodyToActuate from the activate Service: \t" + bodyToActuate);

        Map<String, Object> actuateResult = actuateFetchController.fetchActuateService( actuateServiceUri,bodyToActuate );

        System.out.println("Result to a client from the activate Service: \t" + actuateResult);

        return actuateResult;

    }
}
