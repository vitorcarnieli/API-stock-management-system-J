package br.gov.es.conceicaodocastelo.stock.models;

import java.util.List;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_INSTITUTION")
public class Institution extends BaseEntity {

    @OneToMany(mappedBy = "institution")
    @JsonManagedReference("institution-order")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Order> orders;

    public Institution() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> order) {
        this.orders = order;
    }

    public void addOrders(List<Order> orders) {
        orders.forEach(o -> {
            o.setInstitution(this);
        });
        this.orders.addAll(orders);
    }

    public void addOrders(Order order) {
        order.setInstitution(this);
        this.orders.add(order);
    }

    public void deleteOrder(Order order) {
        this.orders.remove(order);
    }

}
