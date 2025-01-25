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

        Map<String, Object> actuateResult = actuateFetchController.fetchActuateService( actuateServiceUri,bodyToActuate );

        Boolean active = (Boolean) actuateResult.get("success");

        Customer customer = new Customer();
        customer.setIccid( (Long) body.get("iccid") );
        customer.setCustomerEmail( body.get("customerEmail").toString() );
        customer.setActive( active );

        customerDBController.saveCustomer(customer);

        return actuateResult;
    }

    @GetMapping("/customer")
    public ResponseEntity<Object> getCustomerController(@RequestParam Long simCardId ) {

        Optional<Customer> optionalCustomer = customerDBController.findById(simCardId);

        if (optionalCustomer.isPresent()) {
            Map<String, Object> customer = optionalCustomer.get().toMap();
            return ResponseEntity.ok( customer );
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
