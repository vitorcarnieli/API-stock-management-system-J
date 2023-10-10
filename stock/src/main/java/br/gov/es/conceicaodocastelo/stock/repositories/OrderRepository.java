package br.gov.es.conceicaodocastelo.stock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.repositories.generic.GenericRepository;

public interface OrderRepository extends GenericRepository<Order> {
    @Query(value = "SELECT i FROM Order i WHERE i.name LIKE %?1%")
    List<Order> findByNome(String name);
}
