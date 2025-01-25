package au.com.telstra.simcardactivator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Map;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long iccid;
    private String customerEmail;
    private Boolean active;

    protected Customer() {}

    public Customer(Long iccid, String customerEmail, Boolean active ) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    public void setIccid(Long iccid) {
        this.iccid = iccid;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, iccid='%s', customerEmail='%s', active='%s']",
                id, iccid, customerEmail, active);
    }

    public Map<String, Object> toMap() {
        return Map.of( "iccid", iccid, "customerEmail", customerEmail, "active", active);
    }
}