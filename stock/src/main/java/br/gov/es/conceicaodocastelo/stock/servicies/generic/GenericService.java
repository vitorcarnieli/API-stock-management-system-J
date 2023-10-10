package br.gov.es.conceicaodocastelo.stock.servicies.generic;

import java.io.Serializable;
import java.util.List;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;

public interface GenericService<T extends BaseEntity, ID extends Serializable> {
    T save(T entity) throws Exception;
    List<T> saveAll(List<T> entitys) throws Exception;
    T findById(ID id) throws Exception;
    List<T> findAll() throws Exception;
    List<StockGroup> findByNameS(String name) throws Exception;
    List<Item> findByNameI(String name) throws Exception;
    List<Order> findByNameO(String name) throws Exception;
    long count() throws Exception;
    void delete(T entity) throws Exception;
    void deleteById(ID id) throws Exception;
    void deleteAll(List<T> entitys) throws Exception;

}
