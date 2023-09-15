package br.gov.es.conceicaodocastelo.stock.models;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ORDERS")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = -4834257346219938057L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    
    @OneToMany(mappedBy = "order")
    @JsonManagedReference("order-request")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Request> requests;

    
    @ManyToOne
    @JsonBackReference("school-order")
    private School school;

    
    private String observation;

    private Date date;

    
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
    
    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(date == null) {
            this.setDate();
        }
        this.name = name;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> request) {
        request.forEach(r -> r.setOrder(this));
        this.requests = request;
    }

    public void addRequests(Request request) {
        request.setOrder(this);
        this.requests.add(request);
    }

    public void addRequests(List<Request> request) {
        request.forEach(r -> r.setOrder(this));
        this.requests.addAll(request);
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getDateDefault() {
        if(date == null) {
            this.setDate();
        }
        return date;
    }

    public String getDateFormated() {
        if(date == null) {
            this.setDate();
        }
        SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
        return formatData.format(date);
    }
    
    public void setDate() {
        this.date = new Date();
    }



    





}
