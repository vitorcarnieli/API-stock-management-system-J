package br.gov.es.conceicaodocastelo.stock.repositories;

import br.gov.es.conceicaodocastelo.stock.models.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    @Query(value = "SELECT i FROM Item i WHERE i.name LIKE %?1%")
    List<Item> findByNome(String name);

}
