package br.gov.es.conceicaodocastelo.stock.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_STOCK_GROUPS")
public class StockGroupModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -4834257346219938057L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "stockGroup")
    @JsonManagedReference
    private List<ItemModel> items;

    public StockGroupModel() {
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

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }

    public void addItems(ItemModel item) {
        if(item != null) {
            this.items.add(item);
            item.setStockGroup(this);
        } else {
          throw new NullPointerException("addItems(ItemModel item == null)");
        }
    }

    public void addItems(List<ItemModel> items) {
        if(items != null) {
            items.forEach(item -> {
                this.items.add(item);
                item.setStockGroup(this);
            });
        } else {
            throw new NullPointerException("addItems(List<ItemModel> items == null)");
        }
    }
}
