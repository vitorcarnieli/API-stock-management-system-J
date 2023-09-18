package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.dto.OrderRecordDto;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.models.Request;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.repositories.OrderRepository;
import jakarta.persistence.criteria.CriteriaBuilder.In;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    @Autowired
    private ItemService itemService;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void create(OrderRecordDto orderRecordDto) { 
        Order order = new Order();
        Request request = new Request();
        order.setName(orderRecordDto.name());
        order.setObservation(orderRecordDto.observations());
        
        List<List<String>> requests = orderRecordDto.requests();
        for (List<String> list : requests) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                if(i == 0) {
                    request.setItem(itemService.findById(UUID.fromString(list.get(i))).getBody().get());
                } else {
                    request.setRequiredAmount(Integer.parseInt(list.get(i)));
                }
            }
        }
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
