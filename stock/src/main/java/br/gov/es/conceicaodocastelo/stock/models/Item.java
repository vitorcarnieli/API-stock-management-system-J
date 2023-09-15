package br.gov.es.conceicaodocastelo.stock.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "TB_ITEMS")
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = -4834257346219938057L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String unitType;

    private String description;

    private Integer amount;

    
    @OneToMany(mappedBy = "itemModel")
    @JsonManagedReference("item-record")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Records> changes = new ArrayList<>();

    @ManyToOne
    @JsonBackReference("stockGroup-item")    
    private StockGroup stockGroup;


    @OneToMany(mappedBy = "item")
    @JsonManagedReference("item-request") 
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Request> requests;
    
    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void addRequest(List<Request> requests) {
        this.requests.addAll(requests);
    }

    public void addRequest(Request request) {
        this.requests.add(request);
    }

    public Item() {
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public StockGroup getStockGroup() {
        return stockGroup;
    }
    
    public void setStockGroup(StockGroup stockGroup) {
        if(stockGroup != null) {
            this.stockGroup = stockGroup;
        }
        
    }
    
    public Integer getAmount() {
        return amount;
    }
    
    public void setAmount(Integer amount) {
        if(amount != null) {
            if(this.amount == null) {
                addChanges(amount);
            }
            this.amount = amount;
            
        }
    }
    
    public void increaseOrDecreaseAmount(Integer valueToIncreaseOrDecrease) {
        //para incrementar mande um numero positivo, para decrementar mande um negativo
        if(valueToIncreaseOrDecrease != null) {
            if(valueToIncreaseOrDecrease >= 0) {
                this.setAmount(valueToIncreaseOrDecrease + this.getAmount());
            } else {
                this.setAmount(this.getAmount() - (valueToIncreaseOrDecrease * -1));
            }
            addChanges(valueToIncreaseOrDecrease);
        } else {
            throw new NullPointerException("valueToIncreaseOrDecrease == null");
        }
    }
    
    public String getUnitType() {
        return unitType;
    }
    
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public List<Records> getChanges() {
        return changes;
    }

    public void setChanges(List<Records> changes) {
        this.changes = changes;
    }

    public void addChanges(Integer changedValue) {
        Records record = new Records(this);
        if(amount == null) {
            record.setAmount("c"+changedValue);
        }
        if(changes.isEmpty()) {
            changes.add(0, record);
        } else {
            record.setAmount(changedValue.toString());
            this.changes.add(0, record);
        }
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((unitType == null) ? 0 : unitType.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((stockGroup == null) ? 0 : stockGroup.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        return true;
        if (obj == null)
        return false;
        if (getClass() != obj.getClass())
        return false;
        Item other = (Item) obj;
        if (id == null) {
            if (other.id != null)
            return false;
        } else if (!id.equals(other.id))
        return false;
        if (name == null) {
            if (other.name != null)
            return false;
        } else if (!name.equals(other.name))
        return false;
        if (unitType == null) {
            if (other.unitType != null)
            return false;
        } else if (!unitType.equals(other.unitType))
        return false;
        if (description == null) {
            if (other.description != null)
            return false;
        } else if (!description.equals(other.description))
        return false;
        if (amount == null) {
            if (other.amount != null)
            return false;
        } else if (!amount.equals(other.amount))
        return false;
        if (stockGroup == null) {
            if (other.stockGroup != null)
            return false;
        } else if (!stockGroup.equals(other.stockGroup))
            return false;
            return true;
        }
        
        
    }
    