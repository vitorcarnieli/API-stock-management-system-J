package br.gov.es.conceicaodocastelo.stock.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.es.conceicaodocastelo.stock.models.School;

public interface SchoolRepository extends JpaRepository<School, UUID> {
    
}
