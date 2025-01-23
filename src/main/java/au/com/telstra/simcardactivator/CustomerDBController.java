package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDBController {

    @Autowired
    private CustomerRepository repository;

    public void saveCustomer( Customer customer ) {
        repository.save(customer);
    }

    public Optional<Customer> findById( Long id ) {
        return repository.findById(id);
    }

}
