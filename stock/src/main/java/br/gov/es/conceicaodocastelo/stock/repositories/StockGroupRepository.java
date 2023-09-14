package br.gov.es.conceicaodocastelo.stock.repositories;

import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StockGroupRepository extends JpaRepository<StockGroup, UUID> {

    @Query(value = "SELECT s FROM StockGroup s WHERE s.name LIKE %?1%")
    List<StockGroup> findByNome(String name);

}
