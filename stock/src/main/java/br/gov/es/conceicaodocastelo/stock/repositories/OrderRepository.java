package br.gov.es.conceicaodocastelo.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.es.conceicaodocastelo.stock.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
