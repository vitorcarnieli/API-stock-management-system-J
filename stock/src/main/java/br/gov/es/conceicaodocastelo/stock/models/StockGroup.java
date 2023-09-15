package br.gov.es.conceicaodocastelo.stock.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_STOCK_GROUPS")
public class StockGroup implements Serializable {

    @Serial
    private static final long serialVersionUID = -4834257346219938057L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "stockGroup")
    @JsonManagedReference("stockGroup-item")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Item> items;

    public StockGroup() {
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


    @Override
    public String toString() {
        return "StockGroupModel [id=" + id + ", name=" + name + ", description=" + description + ", items=" + items
                + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
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
        StockGroup other = (StockGroup) obj;
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
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (items == null) {
            if (other.items != null)
                return false;
        } else if (!items.equals(other.items))
            return false;
        return true;
    }
    
    
}
