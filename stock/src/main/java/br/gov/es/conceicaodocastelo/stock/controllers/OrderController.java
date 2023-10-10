package br.gov.es.conceicaodocastelo.stock.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.conceicaodocastelo.stock.controllers.generic.GenericControllerImp;
import br.gov.es.conceicaodocastelo.stock.controllers.interfaces.OrderInterface;
import br.gov.es.conceicaodocastelo.stock.dto.OrderCreateRequestDto;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.servicies.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController extends GenericControllerImp<Order> implements OrderInterface{
    
    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderCreateRequestDto request) {
        try {
            try {
                
                Order order = orderService.createOrder(request);
                return ResponseEntity.status(200).body(order);
            } catch (Exception e) {
                return ResponseEntity.status(404).body(e);
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e + "catch 1");
        }
    }


}
