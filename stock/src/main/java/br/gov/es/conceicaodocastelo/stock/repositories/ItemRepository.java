package br.gov.es.conceicaodocastelo.stock.repositories;

import br.gov.es.conceicaodocastelo.stock.models.ItemModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemModel, UUID> {
    @Query(value = "SELECT i FROM ItemModel i WHERE i.name LIKE %?1%")
    List<ItemModel> findByNome(String name);

}
