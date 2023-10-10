package br.gov.es.conceicaodocastelo.stock.repositories.generic;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;

@Repository
public interface GenericRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    //TODO: tentar fazer o findByName funcinar
    
    @Query(value = "SELECT s FROM StockGroup s WHERE s.name LIKE %?1%")
    List<StockGroup> findByNameS(String name);

    @Query(value = "SELECT i FROM Item i WHERE i.name LIKE %?1%")
    List<Item> findByNameI(String name);

    @Query(value = "SELECT o FROM Order o WHERE o.name LIKE %?1%")
    List<Order> findByNameO(String name);
}
