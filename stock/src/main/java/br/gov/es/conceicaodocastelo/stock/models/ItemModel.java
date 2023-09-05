package br.gov.es.conceicaodocastelo.stock.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_ITEMS")
public class ItemModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -4834257346219938057L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String description;

    private Double amount;

    @ManyToOne
    @JsonBackReference
    private StockGroupModel stockGroup;

    public ItemModel() {
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

    public StockGroupModel getStockGroup() {
        return stockGroup;
    }

    public void setStockGroup(StockGroupModel stockGroup) {
        if(stockGroup != null) {
            this.stockGroup = stockGroup;
        }

    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
