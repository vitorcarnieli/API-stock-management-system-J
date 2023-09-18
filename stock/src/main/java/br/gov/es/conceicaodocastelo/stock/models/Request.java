package br.gov.es.conceicaodocastelo.stock.models;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_REQUESTS")
public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = -4834257346219938057L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private Integer requiredAmount;
    
    @ManyToOne
    @JsonBackReference("item-request")    
    private Item item;

    
    @ManyToOne
    @JsonBackReference("order-request")    
    private Order order;

    
    private Date date;
    
    
    
    public Request() {
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return this.order;
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
        item.setAmount(item.getAmount() - this.getRequiredAmount());
        this.item = item;
    }

    
    
}
