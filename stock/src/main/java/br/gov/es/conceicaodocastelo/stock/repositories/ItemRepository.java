package br.gov.es.conceicaodocastelo.stock.repositories;

import br.gov.es.conceicaodocastelo.stock.models.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemModel, UUID> {
}
