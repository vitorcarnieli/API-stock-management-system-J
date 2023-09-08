package br.gov.es.conceicaodocastelo.stock.controllers;

import br.gov.es.conceicaodocastelo.stock.models.ItemModel;
import br.gov.es.conceicaodocastelo.stock.servicies.ItemService;

import java.util.List;
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


}
