package br.gov.es.conceicaodocastelo.stock.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import br.gov.es.conceicaodocastelo.stock.controllers.generic.GenericControllerImp;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.models.Institution;
import br.gov.es.conceicaodocastelo.stock.servicies.InstitutionService;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/institution")
public class InstitutionController extends GenericControllerImp<Institution>{

    final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }
    /*
    @PostMapping(path = "/set")
    public ResponseEntity<String> createInstitution(@RequestBody InstitutionRecordDto institutionDTO) {
        institutionDTO.names().forEach(n -> {
            Institution s = new Institution();
            s.setName(n);
            institutionService.save(s);
        });
        return ResponseEntity.status(HttpStatus.OK)
                .body("ESCOLA : \n" + institutionDTO.names() + "\n salva com sucesso");
    }
    */
    @PostMapping(path = "/set")
    public ResponseEntity<String> addData(@RequestBody List<Institution> institution) {
    	if(institution != null) {
            institution.forEach(i -> {
                institutionService.save(i);
            });
            return ResponseEntity.status(HttpStatus.OK).body("ok");

    	} else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");

    	}
    }

    @PostMapping(path = "/set/order")
    public ResponseEntity<Object> createOrder(@RequestBody Institution institution, @RequestBody Order order) {
        try {
            institution.addOrders(order);
            return ResponseEntity.status(HttpStatus.OK).body(institutionService.save(institution));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("ERROR: " + e.getMessage());
        }
    }

    @GetMapping(path = "/find/all")
    public ResponseEntity<Object> findAllInstitutions() {
        try {
            List<Institution> institutions = institutionService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(institutions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path = "/find/institution")
    public ResponseEntity<Object> findInstitutionInOrder(Order order) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(order.getInstitution());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    
    
}
