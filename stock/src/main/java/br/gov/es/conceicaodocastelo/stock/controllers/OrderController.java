package br.gov.es.conceicaodocastelo.stock.controllers;



import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.conceicaodocastelo.stock.dto.OrderRecordDto;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.servicies.OrderService;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController {
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/find/all")
    public ResponseEntity<Object> findAll() {
        try { 
            List<Order> allOrders = orderService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(allOrders);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + e.getMessage());

        }
    }

    @GetMapping(path = "/find/by/id")
    public ResponseEntity<Object> findById(@RequestParam(name = "id") String id) {
        try { 
            Order order = orderService.findById(UUID.fromString(id));
            order.getRequests().forEach(r -> System.out.println(r.getItem()));
            return ResponseEntity.status(HttpStatus.OK).body(order);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + e.getMessage());

        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRecordDto orderRecordDto) {
        orderService.create(orderRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body("criado");    
    
    }
}
