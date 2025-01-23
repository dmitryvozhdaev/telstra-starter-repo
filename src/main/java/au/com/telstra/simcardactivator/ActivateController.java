package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class ActivateController {

    private final ActuateFetchController actuateFetchController;

    private final CustomerDBController customerDBController;

    String actuateServiceUri = "http://localhost:8444/actuate";

    @Autowired
    public ActivateController(
            ActuateFetchController actuateFetchController,
            CustomerDBController customerDBController ) {
        this.actuateFetchController = actuateFetchController;
        this.customerDBController = customerDBController;
    }

    @PostMapping("/activate")
    public Map<String, Object> postActivateController( @RequestBody Map<String, Object> body ) {
        Map<String, Object> bodyToActuate = Map.of( "iccid", body.get("iccid") );

        System.out.println("Post request body to the activate Service: \t" + body);

        Map<String, Object> actuateResult = actuateFetchController.fetchActuateService( actuateServiceUri,bodyToActuate );

        Boolean active = (Boolean) actuateResult.get("success");
        System.out.println("Result of the activation: \t" + active);

        Customer customer = new Customer();
        customer.setIccid( body.get("iccid").toString() );
        customer.setCustomerEmail( body.get("customerEmail").toString() );
        customer.setActive( active );

        System.out.println("Save customer: \t" + customer.toString());

        customerDBController.saveCustomer(customer);

        System.out.println("Result to a client from the activate Service: \t" + actuateResult);
        return actuateResult;

    }

    @GetMapping("/customer")
    public ResponseEntity<Object> getCustomerController(@RequestParam Long simCardId ) {

        System.out.println("Get request simCardId from a client: \t" + simCardId);

        Optional<Customer> optionalCustomer = customerDBController.findById(simCardId);

        if (optionalCustomer.isPresent()) {
            Map<String, Object> customer = optionalCustomer.get().toMap();
            System.out.println("Found customer requested: \t" + customer);
            return ResponseEntity.ok( customer );
        }
        else {
            System.out.println("Not found customer requested");
            return ResponseEntity.notFound().build();
        }
    }
}
