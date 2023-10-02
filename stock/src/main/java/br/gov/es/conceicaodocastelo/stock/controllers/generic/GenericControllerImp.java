package br.gov.es.conceicaodocastelo.stock.controllers.generic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;
import br.gov.es.conceicaodocastelo.stock.servicies.generic.GenericServiceImp;

public class GenericControllerImp<T extends BaseEntity> implements GenericController<T> {
    @Autowired
    private GenericServiceImp<T> genericService;

    @Override
    @PostMapping
    public ResponseEntity<Object> save(T entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(genericService.save(entity));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: save(T entity)");
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<List<T>> findAll() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(genericService.findAll());
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(genericService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: findById()");
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        try {
            genericService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @Override
    @DeleteMapping()
    public ResponseEntity<Boolean> delete(T entity) {
        try {
            genericService.delete(entity);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    
    @Override
    @DeleteMapping(path = "/all")
    public ResponseEntity<Boolean> deleteAll() {
        try {
            List<T> entitys = this.findAll().getBody();
            genericService.deleteAll(entitys);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
    

    @Override
    @GetMapping("/s/{name}")
    public ResponseEntity<Object> findByNameS(@PathVariable("name") String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(genericService.findByNameS(name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("not found be like");
        }
    }

    @Override
    @GetMapping("/i/{name}")
    public ResponseEntity<Object> findByNameI(@PathVariable("name") String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(genericService.findByNameI(name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("not found be like");
        }
    }
    

}
