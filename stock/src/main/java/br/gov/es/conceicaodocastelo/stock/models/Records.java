package br.gov.es.conceicaodocastelo.stock.models;

import java.io.Serial;
import java.io.Serializable;
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
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_RECORDS")
public class Records implements Serializable {

    @Serial
    private static final long serialVersionUID = -4834257346219938057L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String date;
    private String amount;

    @ManyToOne
    @JsonBackReference("item-record")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Item itemModel;

    
    
    public Records() {
    }

    public Records(Item itemModel) {
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

    public Item getItemModel() {
        return itemModel;
    }

    public void setItemModel(Item itemModel) {
        this.itemModel = itemModel;
    }

    @Override
    public String toString() {
        return "Records [id=" + id + ", date=" + date + ", amount=" + amount + ", itemModel=" + itemModel + "]";
    }

    

    
}

