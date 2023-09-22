package br.gov.es.conceicaodocastelo.stock.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "TB_ITEMS")
public class Item extends BaseEntity {

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

    public String getStockId() {
        return this.getStockGroup().getId().toString();
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
        if (stockGroup != null) {
            this.stockGroup = stockGroup;
        }

    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        if (amount != null) {
            if (this.amount == null) {
                addChanges(amount.toString());
            }
            this.amount = amount;

        }
    }

    public void increaseOrDecreaseAmount(Integer valueToIncreaseOrDecrease) {
        // para incrementar mande um numero positivo, para decrementar mande um negativo
        if (valueToIncreaseOrDecrease != null) {
            if (valueToIncreaseOrDecrease >= 0) {
                this.setAmount(valueToIncreaseOrDecrease + this.getAmount());
            } else {
                this.setAmount(this.getAmount() - (valueToIncreaseOrDecrease * -1));
            }
            addChanges(valueToIncreaseOrDecrease.toString());
        } else {
            throw new NullPointerException("valueToIncreaseOrDecrease == null");
        }
    }

    public void increaseOrDecreaseAmountInstitution(Integer valueToIncreaseOrDecrease, String institutionName) {
        // para incrementar mande um numero positivo, para decrementar mande um negativo
        if (valueToIncreaseOrDecrease != null) {
            if (valueToIncreaseOrDecrease >= 0) {
                this.setAmount(valueToIncreaseOrDecrease + this.getAmount());
            } else {
                this.setAmount(this.getAmount() - (valueToIncreaseOrDecrease * -1));
            }
            addChanges(institutionName + "," + valueToIncreaseOrDecrease.toString());
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

    public void addChanges(String changedValue) {
        Records record = new Records(this);
        if (amount == null) {
            record.setAmount("c" + changedValue);
        }
        if (changes.isEmpty()) {
            changes.add(0, record);
        } else {
            record.setAmount(changedValue.toString());
            this.changes.add(0, record);
        }
    }

}
