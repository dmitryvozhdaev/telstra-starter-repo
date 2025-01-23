package au.com.telstra.simcardactivator;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findById(long id);
}