package br.gov.es.conceicaodocastelo.stock.controllers;

import br.gov.es.conceicaodocastelo.stock.controllers.generic.GenericControllerImp;
import br.gov.es.conceicaodocastelo.stock.controllers.interfaces.ItemInterface;
import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.servicies.ItemService;

import java.util.List;

import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/item")
public class ItemController extends GenericControllerImp<Item> implements ItemInterface{

    @Autowired
    private ItemService itemService;

    // GETER'S
    @GetMapping(path = "/find/byName")
    @ResponseBody
    public ResponseEntity<List<Item>> findByName(@RequestParam(value = "idGroup") String idGroup,
            @RequestParam(value = "name") String name) {
        Long id = Long.parseLong(idGroup);
        if (itemService.findByName(name) != null) {
            List<Item> items = itemService.findByName(name);
            items.removeIf(i -> !i.getStockGroup().getId().equals(id));
            return ResponseEntity.status(HttpStatus.OK).body(items);
        } else {
            throw new QueryException("not found");
        }
    }

    @PostMapping(path = "/add-changes")
    @ResponseBody
    public ResponseEntity<Object> addChanges(@RequestParam(value = "id") Long id, @RequestParam(value = "c") Integer change) {
        ResponseEntity<Object> query = this.findById(id);
        if(query.getStatusCode().is2xxSuccessful()) {
            Item item = (Item) query.getBody();
            boolean ok = item.increaseOrDecreaseAmount(change);
            if(ok) {
                this.save(item);
                return ResponseEntity.status(HttpStatus.OK).body("OK");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERRO DURANTE INSERCAO");

            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ITEM NOT FOUND BY ID");
        }
    }

    

}
