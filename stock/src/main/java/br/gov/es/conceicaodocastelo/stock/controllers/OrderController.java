package br.gov.es.conceicaodocastelo.stock.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.conceicaodocastelo.stock.dto.OrderRecordDto;
import br.gov.es.conceicaodocastelo.stock.servicies.OrderService;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController {
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRecordDto orderRecordDto) {
        orderService.create(orderRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body("criado");    
    
    }
}
