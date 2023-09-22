package br.gov.es.conceicaodocastelo.stock.repositories;

import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.repositories.generic.GenericRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockGroupRepository extends GenericRepository<StockGroup> {

    @Query(value = "SELECT s FROM StockGroup s WHERE s.name LIKE %?1%")
    List<StockGroup> findByNome(String name);


}
