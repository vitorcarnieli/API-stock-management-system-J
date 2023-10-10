package br.gov.es.conceicaodocastelo.stock.controllers;

import java.util.List;

import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    
    @GetMapping(path = "/find/byName")
    @ResponseBody
    public ResponseEntity<List<Order>> findByName(@RequestParam(value = "id") Long idIns,
            @RequestParam(value = "name") String name) {
        if (orderService.findByName(name) != null) {
            List<Order> orders = orderService.findByName(name);
            orders.removeIf(o -> !o.getInstitution().getId().equals(idIns));
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } else {
            throw new QueryException("not found");
        }
    }



}
