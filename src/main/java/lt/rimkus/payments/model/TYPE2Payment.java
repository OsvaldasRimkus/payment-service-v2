package lt.rimkus.payments.model;

import jakarta.persistence.Entity;

@Entity
public class TYPE2Payment extends Payment {
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
