package br.gov.es.conceicaodocastelo.stock.controllers;

import br.gov.es.conceicaodocastelo.stock.controllers.generic.GenericControllerImp;
import br.gov.es.conceicaodocastelo.stock.controllers.interfaces.ItemInterface;
import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.servicies.ItemService;
import br.gov.es.conceicaodocastelo.stock.servicies.StockGroupService;

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

    @Autowired
    private StockGroupService stockGroupService;

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

    @GetMapping(path = "/find/byId")
    @ResponseBody
    public ResponseEntity<Object> findById(@RequestParam(value = "id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(itemService.findById(Long.parseLong(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ID NOT FOUND");
        }
    }

    @PostMapping(path = "/add/changes")
    @ResponseBody
    public ResponseEntity<Object> addChanges(@RequestParam(value = "idItem") String idItem,
            @RequestParam(value = "change") Integer change) {
        if (idItem != null && change != null) {
            Item item = itemService.addChanges(Long.parseLong(idItem), change);
            Integer size = item.getChanges().size();
            if (size != item.getChanges().size()) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                throw new RuntimeException("erro de inserção");
            }
        } else {
            throw new NullPointerException("null");
        }
    }


}
