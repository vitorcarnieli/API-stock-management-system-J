package br.gov.es.conceicaodocastelo.stock.repositories;

import br.gov.es.conceicaodocastelo.stock.models.StockGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StockGroupRepository extends JpaRepository<StockGroupModel, UUID> {

    @Query(value = "SELECT s FROM StockGroupModel s WHERE s.name LIKE %?1%")
    List<StockGroupModel> findByNome(String name);

}
