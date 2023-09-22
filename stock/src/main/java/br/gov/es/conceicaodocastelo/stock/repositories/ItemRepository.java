package br.gov.es.conceicaodocastelo.stock.repositories;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.repositories.generic.GenericRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends GenericRepository<Item> {
    @Query(value = "SELECT i FROM Item i WHERE i.name LIKE %?1%")
    List<Item> findByNome(String name);

}
