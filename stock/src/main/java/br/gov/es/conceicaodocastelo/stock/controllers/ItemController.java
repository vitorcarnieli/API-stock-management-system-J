package br.gov.es.conceicaodocastelo.stock.controllers;

import br.gov.es.conceicaodocastelo.stock.models.ItemModel;
import br.gov.es.conceicaodocastelo.stock.models.StockGroupModel;
import br.gov.es.conceicaodocastelo.stock.servicies.ItemService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.QueryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


        //GETER'S
    @GetMapping(path = "/find/byName")
    @ResponseBody
    public ResponseEntity<List<ItemModel>> findByName(@RequestParam(value = "idGroup") String idGroup,@RequestParam(value = "name") String name) {
        UUID id = UUID.fromString(idGroup);
        if(itemService.findByName(name).getBody() != null) {
            List<ItemModel> items = itemService.findByName(name).getBody();
            items.removeIf(i -> !i.getStockGroup().getId().equals(id));
            return ResponseEntity.status(HttpStatus.OK).body(items);
        } else {
            throw new QueryException("not found");
        }
    }

    @GetMapping(path = "/find/byId")
    @ResponseBody
    public ResponseEntity<ItemModel> findById(@RequestParam(value = "id") String idd) {
        UUID id = UUID.fromString(idd);
        if(id != null) {
            Optional<ItemModel> item = itemService.findById(id).getBody();
            if(item.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(item.get());
            } else {
                throw new RuntimeException("not found");
            }
        } else {
            throw new QueryException("null pointer");
        }
    }

    
    @PostMapping(path = "/add/changes")
    @ResponseBody
    public ResponseEntity<Object> addChanges(@RequestParam(value = "idItem") String idItem, @RequestParam(value = "change") Integer change) {
        if (idItem != null && change != null) {
            ResponseEntity<ItemModel> response = itemService.addChanges(UUID.fromString(idItem), change);

            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                throw new RuntimeException("erro de inserção");
            }
        } else {
            throw new NullPointerException("null");
        }
    }

    @DeleteMapping(path = "/delete")
    @ResponseBody
    public ResponseEntity<StockGroupModel> delete(@RequestParam(value = "idItem") String idItem) {
        StockGroupModel stock = itemService.deleteById(UUID.fromString(idItem));
        return ResponseEntity.status(HttpStatus.OK).body(stock);
        
    }
    

}
