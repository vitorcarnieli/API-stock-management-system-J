package br.gov.es.conceicaodocastelo.stock.controllers;
    
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.conceicaodocastelo.stock.dto.SchoolRecordDto;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.models.School;
import br.gov.es.conceicaodocastelo.stock.servicies.SchoolService;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/school")
public class SchoolController {

    final SchoolService schoolService;

    

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }



    @PostMapping(path = "/set")
    public ResponseEntity<String> createSchool(@RequestBody SchoolRecordDto schoolDTO) {
        schoolDTO.names().forEach(n -> {
            School s = new School();
            s.setName(n);
            schoolService.save(s);
        });
        return ResponseEntity.status(HttpStatus.OK).body("ESCOLA : \n" + schoolDTO.names() + "\n salva com sucesso");
    }
    
    @PostMapping(path = "/set/order")
    public ResponseEntity<Object> createOrder(@RequestBody School school, @RequestBody Order order) {
        try {
            school.addOrders(order);
            return ResponseEntity.status(HttpStatus.OK).body(schoolService.save(school));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("ERROR: " + e.getMessage());
        }
    }



    @GetMapping(value = "/find/byId")
    @ResponseBody
    public ResponseEntity<Object> findById(@RequestParam(value = "id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(schoolService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: " + e.getMessage());
        }
    }

    @GetMapping(path = "/find/all")
    public ResponseEntity<Object> findAllSchools() {
        try {
            List<School> schools = schoolService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(schools);        
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/find/school")
    public ResponseEntity<Object> findSchoolInOrder(Order order) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(order.getSchool());        
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/delete")
    public void delete() {
        schoolService.deleteAll();
    }

}
