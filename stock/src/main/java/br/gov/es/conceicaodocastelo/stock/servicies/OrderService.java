package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.dto.OrderRecordDto;
import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.models.Request;
import br.gov.es.conceicaodocastelo.stock.repositories.OrderRepository;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private RequestServicie requestServicie;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void create(OrderRecordDto orderRecordDto) { 
        Order order = new Order();
        order.setName(orderRecordDto.name());
        order.setObservation(orderRecordDto.observations());
        order.setSchool(schoolService.findById(orderRecordDto.school()));
        Item item = null;
        Integer amount;
        List<Request> requests = new ArrayList<>();
        
        List<List<String>> requestsFromList = orderRecordDto.requests();
        for (List<String> list : requestsFromList) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                if(i == 0) {
                    item = itemService.findById(UUID.fromString(list.get(i))).getBody().get();
                    continue;
                } else {
                    System.out.println(item);
                    amount = Integer.parseInt(list.get(i));
                    Request request = new Request();
                    request.setRequiredAmount(amount);
                    request.setItem(item);
                    requests.add(request);  
                    request.setOrder(order);
                    requestServicie.save(request);
                    orderRepository.save(order);
                }
            }
        }
    }

    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()) {
            throw new RuntimeException("orders not found");
        }
        return orders;
    }

    public Order findById(UUID orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()) {
            throw new RuntimeException("order id not founded");
        }
        return order.get();
    }

    public boolean delete(Order order) {
        try {
            orderRepository.delete(order);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public List<Request> findAllRequests(Order order) {
        return order.getRequests();
    }
    

    
}
