package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.repositories.OrderRepository;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()) {
            throw new RuntimeException("orders not found");
        } else {
            return orders;
        }
    }
    
    
}
