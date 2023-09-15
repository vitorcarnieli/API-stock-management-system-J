package br.gov.es.conceicaodocastelo.stock.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_SCHOOL")
public class School implements Serializable {

    @Serial
    private static final long serialVersionUID = -4834257346219938057L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    
    @OneToMany(mappedBy = "requesterSchool")
    @JsonManagedReference("school-request")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Request> requests;

    

    public School() {
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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
        this.name = name;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
        requests.forEach(r -> r.setRequesterSchool(this));
    }

    public void addRequest(Request request) {
        this.requests.add(request);
        request.setRequesterSchool(this);
    }

    public void addRequest(List<Request> requests) {
        this.requests.addAll(requests);
        requests.forEach(r -> r.setRequesterSchool(this));
    }

    public void deleteRequest(Request request) {
        this.requests.remove(request);
    }


    
}
