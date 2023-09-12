package br.gov.es.conceicaodocastelo.stock.controllers;

import br.gov.es.conceicaodocastelo.stock.dto.ItemRecordDto;
import br.gov.es.conceicaodocastelo.stock.dto.StockGroupRecordDto;
import br.gov.es.conceicaodocastelo.stock.models.ItemModel;
import br.gov.es.conceicaodocastelo.stock.models.StockGroupModel;
import br.gov.es.conceicaodocastelo.stock.servicies.StockGroupService;
import jakarta.validation.Valid;
import org.hibernate.QueryException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin("*")
@RequestMapping(path = "/stock-group")
public class StockGroupController {


    final StockGroupService stockGroupService;

    public StockGroupController(StockGroupService stockGroupService) {
        this.stockGroupService = stockGroupService;
    }

    private List<StockGroupModel> stocks = new ArrayList<>();

    StockGroupModel stockAwaitForReturn;

    //POST'S
    @PostMapping(value = "/create")
    public ResponseEntity<StockGroupModel> createStockGroup(@RequestBody @Valid StockGroupRecordDto stockGroupRecordDto ) {
        System.out.println("Recebendo solicitação POST em /stock-group/create");
        StockGroupModel stockGroup = new StockGroupModel();
        BeanUtils.copyProperties(stockGroupRecordDto,stockGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockGroupService.save(stockGroup));
    }

    @PostMapping(value = "/addItem/{idGroup}")
    public ResponseEntity<StockGroupModel> addItemToStockGroup(@PathVariable(value = "idGroup") UUID idGroup, @RequestBody @Valid ItemRecordDto itemRecordDto) {
        ItemModel item = new ItemModel();

        BeanUtils.copyProperties(itemRecordDto, item);

        Optional<StockGroupModel> stockO = stockGroupService.findById(idGroup);

        if (stockO.isEmpty()) {
            throw new QueryException("StockGroup not found");
        }

        StockGroupModel stockM = stockO.get();
        stockM.addItems(item);

        stockM.getItems().forEach(i -> {
            if(i.getUnitType() == null || i.getUnitType() == "") {
                i.setUnitType("Unidade");
            }
        });


        return ResponseEntity.status(HttpStatus.OK).body(stockGroupService.save(stockM));
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<StockGroupModel> transferItemFromOneStockToAnother(@RequestParam UUID recipientId,@RequestParam UUID senderId,@RequestParam UUID transferredItem) {
        if(recipientId != null && senderId != null && transferredItem != null) {

            Optional<StockGroupModel> sender = stockGroupService.findById(senderId);
            Optional<StockGroupModel> recipient = stockGroupService.findById(recipientId);

            if(sender.isPresent() && recipient.isPresent()) {

                StockGroupModel senderStock = sender.get();
                StockGroupModel recipientStock = recipient.get();
                Optional<ItemModel> item = stockGroupService.findItemInStockGroupById(senderStock, transferredItem);

                if(item.isPresent()) {
                    ItemModel itemModel = item.get();

                    stockGroupService.deleteItemInStockGroup(senderStock, itemModel);
                    stockGroupService.save(senderStock);

                    recipientStock.addItems(itemModel);
                    stockGroupService.save(recipientStock);

                    return ResponseEntity.status(HttpStatus.OK).body(recipientStock);

                } else {
                    throw new RuntimeException("item not found");
                }
            } else {
                throw new RuntimeException("sender of recipient not found");
            }

        } else {
            throw new NullPointerException("Null params");
        }
    }


    //GETER'S
    @GetMapping(path = "/find/byName")
    @ResponseBody
    public ResponseEntity<List<StockGroupModel>> findByName(@RequestParam(value = "name") String name) {
        if(stockGroupService.findByName(name).getBody() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(stockGroupService.findByName(name).getBody());
        } else {
            throw new QueryException("not found");
        }
    }

    @GetMapping(value = "/find/byId")
    @ResponseBody
    public ResponseEntity<Optional<StockGroupModel>> findById(@RequestParam(value = "id") UUID id) {
        if(id != null) {
            Optional<StockGroupModel> stock = stockGroupService.findById(id);
            if(stock.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(stockGroupService.findById(id));
            } else {
                throw new QueryException("not found");
            }
        } else {
            throw new NullPointerException("ResponseEntity<Optional<StockGroupModel>> findById(@RequestParam(value = \"id\") UUID id == null)");
        }
    }



    @GetMapping(path = "/find/all")
    @ResponseBody
    public List<StockGroupModel> findAll() {
        stocks.clear();
        stocks = stockGroupService.findAll();
        return stocks;
    }

    @GetMapping(path = "/getNames")
    @ResponseBody
    public List<String> getAllStockNamesAndDescription() {
        List<String> l = new ArrayList<>();
        l.clear();
        findAll().forEach(s -> {
            l.add(s.getName());
            String d = s.getDescription();
            if(d != null) {
                l.add(s.getDescription());
            } else {
                l.add("");
            }
        });
        System.out.println(l.toString());
        return l;

    }

    @GetMapping(value = "/getAllItems")
    @ResponseBody
    public List<ItemModel> getAllItemInStockGroup(@RequestParam(value = "idGroup") UUID id) {
        if(id != null) {
            Optional<StockGroupModel> stock = findById(id).getBody();
            if(stock != null && stock.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(stock.get().getItems()).getBody();
            } else {
                throw new QueryException("stock not found");
            }
        }
        throw new NullPointerException("List<ItemModel> getAllItemInStockGroup(@RequestParam(value = \"idGroup\") UUID id == null)");
    }


    //DESTROYERS
    @GetMapping("/delete")
    @ResponseBody
    public void deleteStockGroupById(@RequestParam(value = "idGroup") UUID id) {
        if(id != null) {
            stockGroupService.deleteById(id);
        }
    }

    @DeleteMapping("/delete/item")
    @ResponseBody
    public StockGroupModel deleteItem(@RequestParam(value = "idItem") UUID itemId) {
        if(itemId != null) {
            StockGroupModel stockGroup = stockGroupService.findStockGroupByItem(itemId);
            ItemModel item = stockGroupService.findItemInStockGroupById(stockGroup, itemId).get();
            stockGroupService.deleteItemInStockGroup(stockGroup, item);
            return stockGroup;
        } else {
            throw new NullPointerException("null");
        }
    }

    @GetMapping(value = "/revice")
    public void reviceStockObject(@RequestParam(value = "id") UUID id) {
        stockAwaitForReturn = findById(id).getBody().get();
    }

    @GetMapping(value = "/send")
    public ResponseEntity<StockGroupModel> sendStockObject() {
        if(stockAwaitForReturn != null) {
            return ResponseEntity.status(HttpStatus.OK).body(stockAwaitForReturn);
        } else {
            return null;
        }
    }

    
    
}
