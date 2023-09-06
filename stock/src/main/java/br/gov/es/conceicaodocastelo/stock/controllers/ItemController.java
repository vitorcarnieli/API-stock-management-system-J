package br.gov.es.conceicaodocastelo.stock.controllers;

import br.gov.es.conceicaodocastelo.stock.servicies.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {

    final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public void editDescriptionItem() {}



}
