package br.gov.es.conceicaodocastelo.stock.servicies;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
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

    public StockGroup save(StockGroup stockGroupModel) {
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



    public Optional<StockGroup> findById(@PathVariable UUID id) {
        if(id != null) {
            return stockGroupRepository.findById(id);
        } else {
            throw new NullPointerException("Optional<StockGroupModel> findById(UUID id)");
        }
    }

    public ResponseEntity<List<StockGroup>> findByName(@RequestParam(value = "name") String name) {
        if(name != null) {
            List<StockGroup> stocks = stockGroupRepository.findByNome(name);
            return new ResponseEntity<List<StockGroup>>(stocks, HttpStatus.OK);
        } else {
            throw new NullPointerException("ResponseEntity<List<StockGroupModel>> findByName(@PathVariable String name == null)");
        }
    }


    public List<StockGroup> findAll() {
        return stockGroupRepository.findAll();
    }

    public void delete(StockGroup stockGroupModel) {
        if(stockGroupModel != null) {
            stockGroupRepository.delete(stockGroupModel);
        } else {
            throw new NullPointerException("public void delete(StockGroupModel stockGroupModel == null)");
        }
    }

    public Optional<Item> findItemInStockGroupById(StockGroup stockGroupModel, UUID id) {
        if(stockGroupModel != null) {
            Optional<Item> item = this.findAllItemsInStockGroup(stockGroupModel).stream().filter(i -> i.getId().equals(id)).findFirst();
            if(item.isPresent()) {
                return item;
            } else {
                throw new RuntimeException("item not funded");
            }
        } else {
            throw new NullPointerException("null arg");
        }
    }

    public  StockGroup findStockGroupByItem(UUID itemId) {
        return itemService.findById(itemId).getBody().get().getStockGroup();
    }

    public List<Item> findAllItemsInStockGroup(StockGroup stockGroupModel) {
        if(stockGroupModel != null) {
            List<Item> items = stockGroupModel.getItems();
            if(items.isEmpty()) {
                throw new RuntimeException("empty list");
            } else {
                return items;
            }
        } else {
            throw new NullPointerException("null arg");
        }
    }



    public void deleteById(UUID id) {
        if(id != null) {
            Optional<StockGroup> stock = this.findById(id);
            if(stock.isPresent()) {
                stockGroupRepository.delete(stock.get());
            } else {
                throw new RuntimeException("not found by id");
            }
        } else {
            throw new NullPointerException("public void deleteById(UUID id == null)");
        }
    }

    public void deleteAll() {
        stockGroupRepository.deleteAll();
    }


}
