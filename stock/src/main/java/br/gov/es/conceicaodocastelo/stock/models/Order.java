package br.gov.es.conceicaodocastelo.stock.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ORDERS")
public class Order extends BaseEntity {

    @OneToMany(mappedBy = "order")
    @JsonManagedReference("order-request")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Request> requests = new ArrayList<>();

    @ManyToOne
    @JsonBackReference("institution-order")
    private Institution institution;

    private String observation;

    private Date date;

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        if (date == null) {
            this.setDate();
        }
        this.institution = institution;
    }

    public String getInstitutionId() {
        return institution.getId().toString();
    }

    public Order() {
        this.setDate();
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> request) {
        if (date == null) {
            this.setDate();
        }
        request.forEach(r -> r.setOrder(this));
        this.requests = request;
    }

    public void addRequests(Request request) {
        if (date == null) {
            this.setDate();
        }
        this.requests.add(request);
    }

    public void addRequests(List<Request> request) {
        if (date == null) {
            this.setDate();
        }
        request.forEach(r -> r.setOrder(this));
        this.requests.addAll(request);
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        if (date == null) {
            this.setDate();
        }
        this.observation = observation;
    }

    public Date getDateDefault() {
        if (date == null) {
            this.setDate();
        }
        return date;
    }

    public String getDateFormated() {
        if (date == null) {
            this.setDate();
        }
        SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
        return formatData.format(date);
    }

    public void setDate() {
        this.date = new Date();
    }

}
