package br.gov.es.conceicaodocastelo.stock.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private Integer requiredAmount;

    
    @ManyToOne
    private School requesterSchool;
    
    @ManyToOne
    private Item item;

    
    private Date date;
    
    
    
    public Request() {
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
    
    public School getRequesterSchool() {
        return requesterSchool;
    }
    
    public void setRequesterSchool(School requesterSchool) {
        this.requesterSchool = requesterSchool;
    }
    
    public Date getDateDefault() {
        return date;
    }

    public String getDateFormated() {
        SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
        return formatData.format(date);
    }
    
    public void setDate() {
        this.date = new Date();
    }
    
    public Integer getRequiredAmount() {
        return requiredAmount;
    }
    
    public void setRequiredAmount(Integer requiredAmount) {
        this.requiredAmount = requiredAmount;
    }
    
    public Item getItem() {
        return item;
    }
    
    public void setItems(Item item) {
        if(item.getAmount() - this.getRequiredAmount() < 0) {
            throw new RuntimeException("limit out range");
        }
        this.item = item;
    }
    
}
