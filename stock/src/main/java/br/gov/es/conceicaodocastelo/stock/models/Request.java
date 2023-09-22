package br.gov.es.conceicaodocastelo.stock.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_REQUESTS")
public class Request extends BaseEntity {

    private Integer requiredAmount;

    @ManyToOne
    @JsonBackReference("item-request")
    private Item item;

    @ManyToOne
    @JsonBackReference("order-request")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
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
        this.setDate();
        this.requiredAmount = requiredAmount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        if (item.getAmount() - this.getRequiredAmount() < 0) {
            throw new RuntimeException("limit out range");
        }
        item.increaseOrDecreaseAmountInstitution(-this.getRequiredAmount(), order.getInstitution().getName());
        this.item = item;
    }

    public String getItemName() {
        return this.getItem().getName();
    }

    public String getStockGroupItemName() {
        return this.getItem().getStockGroup().getName();
    }

}
