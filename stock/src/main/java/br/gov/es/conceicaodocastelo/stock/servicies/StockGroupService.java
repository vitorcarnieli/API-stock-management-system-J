package br.gov.es.conceicaodocastelo.stock.servicies;

import br.gov.es.conceicaodocastelo.stock.models.ItemModel;
import br.gov.es.conceicaodocastelo.stock.models.StockGroupModel;
import br.gov.es.conceicaodocastelo.stock.repositories.ItemRepository;
import br.gov.es.conceicaodocastelo.stock.repositories.StockGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StockGroupService {
    @Autowired
    StockGroupRepository stockGroupRepository;

    @Autowired
    ItemService itemService;

    public StockGroupModel save(StockGroupModel stockGroupModel) {
        if(stockGroupModel != null) {
            if(stockGroupModel.getItems() != null) {
                stockGroupModel.getItems().forEach(item -> {
                    itemService.save(item);
                });
            }

            return stockGroupRepository.save(stockGroupModel);
        }
        throw new NullPointerException("StockGroupModel save(StockGroupModel stockGroupModel == null)");
    }

    public Optional<StockGroupModel> findById(@PathVariable UUID id) {
        if(id != null) {
            return stockGroupRepository.findById(id);
        } else {
            throw new NullPointerException("Optional<StockGroupModel> findById(UUID id)");
        }
    }

    public ResponseEntity<List<StockGroupModel>> findByName(@RequestParam(value = "name") String name) {
        if(name != null) {
            List<StockGroupModel> stocks = stockGroupRepository.findByNome(name);
            return new ResponseEntity<List<StockGroupModel>>(stocks, HttpStatus.OK);
        } else {
            throw new NullPointerException("ResponseEntity<List<StockGroupModel>> findByName(@PathVariable String name == null)");
        }
    }


    public List<StockGroupModel> findAll() {
        return stockGroupRepository.findAll();
    }

    public void delete(StockGroupModel stockGroupModel) {
        if(stockGroupModel != null) {
            stockGroupRepository.delete(stockGroupModel);
        } else {
            throw new NullPointerException("public void delete(StockGroupModel stockGroupModel == null)");
        }
    }

    public void deleteById(UUID id) {
        if(id != null) {
            stockGroupRepository.deleteById(id);
        } else {
            throw new NullPointerException("public void deleteById(UUID id == null)");
        }
    }
}
