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

	private static final long serialVersionUID = -4409232857182759514L;

	@OneToMany(mappedBy = "institution")
    @JsonManagedReference("institution-order")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Order> orders;
    
    private String responsible;
    
    private String email;
    
    private String contatcPhone;
    
    private String adress;

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

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContatcPhone() {
		return contatcPhone;
	}

	public void setContatcPhone(String contatcPhone) {
		this.contatcPhone = contatcPhone;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    

}
