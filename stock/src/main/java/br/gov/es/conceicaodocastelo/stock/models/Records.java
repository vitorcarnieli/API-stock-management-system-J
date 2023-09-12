package br.gov.es.conceicaodocastelo.stock.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Records {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String date;
    private String amount;

    @ManyToOne
    @JsonBackReference
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private ItemModel itemModel;

    
    
    public Records() {
    }

    public Records(ItemModel itemModel) {
        setItemModel(itemModel);
        LocalDateTime now = LocalDateTime.now();
        
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
        date = now.format(pattern);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ItemModel getItemModel() {
        return itemModel;
    }

    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
    }

    @Override
    public String toString() {
        return "Records [id=" + id + ", date=" + date + ", amount=" + amount + ", itemModel=" + itemModel + "]";
    }

    

    
}

