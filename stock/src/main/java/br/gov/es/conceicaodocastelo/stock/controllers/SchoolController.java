package br.gov.es.conceicaodocastelo.stock.controllers;
    
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.conceicaodocastelo.stock.dto.SchoolRecordDto;
import br.gov.es.conceicaodocastelo.stock.models.Request;
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
            School s = new School();
            s.setName(schoolDTO.name());
            schoolService.save(s);
        return ResponseEntity.status(HttpStatus.OK).body("ESCOLA : \n" + schoolDTO.name() + "\n salva com sucesso");
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

    @PostMapping(path = "/create/request")
    public ResponseEntity<Object> createRequestIntoSchool(School school, Request request) {
        try {
            schoolService.createRequestIntoSchool(school, request);
            return ResponseEntity.status(HttpStatus.OK).body(school);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/request")
    public void deleteRequest(School school, Request request) {
        try {
            schoolService.deleteRequest(school, request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
