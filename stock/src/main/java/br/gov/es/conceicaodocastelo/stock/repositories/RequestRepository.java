package br.gov.es.conceicaodocastelo.stock.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.es.conceicaodocastelo.stock.models.Request;

public interface RequestRepository extends JpaRepository<Request, UUID>{
    
}
