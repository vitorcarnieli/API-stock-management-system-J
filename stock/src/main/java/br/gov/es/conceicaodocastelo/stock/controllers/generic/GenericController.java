package br.gov.es.conceicaodocastelo.stock.controllers.generic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;

public interface GenericController<T extends BaseEntity> {
    ResponseEntity<Object> save(@RequestBody T entity);

    ResponseEntity<Object> findAll();

    ResponseEntity<Object> findById(@PathVariable Long id);

    ResponseEntity<Boolean> delete(@PathVariable Long id);

    
    ResponseEntity<Object> findByNameS(@PathVariable String name);

    ResponseEntity<Object> findByNameI(@PathVariable String name);

}
