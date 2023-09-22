package br.gov.es.conceicaodocastelo.stock.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "TB_STOCK_GROUPS")
public class StockGroup extends BaseEntity {

    private String description;

    @OneToMany(mappedBy = "stockGroup")
    @JsonManagedReference("stockGroup-item")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Item> items;

    public StockGroup() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItems(Item item) {
        if(item != null) {
            this.items.add(item);
            item.setStockGroup(this);
        } else {
          throw new NullPointerException("addItems(ItemModel item == null)");
        }
    }

    public void addItems(List<Item> items) {
        if(items != null) {
            items.forEach(item -> {
                this.items.add(item);
                item.setStockGroup(this);
            });
        } else {
            throw new NullPointerException("addItems(List<ItemModel> items == null)");
        }
    }

    public void deleteItem(Item item) {
        this.items.remove(item);
    }

    public void deleteItems() {
        this.items.clear();
    }

    
    
}
