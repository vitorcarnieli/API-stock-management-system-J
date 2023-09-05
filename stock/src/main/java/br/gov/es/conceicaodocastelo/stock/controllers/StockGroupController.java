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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/stock-group")
public class StockGroupController {


    final StockGroupService stockGroupService;

    public StockGroupController(StockGroupService stockGroupService) {
        this.stockGroupService = stockGroupService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<StockGroupModel> createStockGroup(@RequestBody @Valid StockGroupRecordDto stockGroupRecordDto ) {
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

        stockM.getItems().forEach(i -> System.out.println(i.getName()));


        return ResponseEntity.status(HttpStatus.OK).body(stockGroupService.save(stockM));
    }







    @GetMapping(path = "/find/byName")
    @ResponseBody
    public List<StockGroupModel> findByName(@RequestParam(value = "name") String name) {
        if(stockGroupService.findByName(name).getBody() != null) {
            return stockGroupService.findByName(name).getBody();
        } else {
            throw new QueryException("not found");
        }
    }

    @GetMapping(value = "/find/byId")
    @ResponseBody
    public Optional<StockGroupModel> findById(@RequestParam(value = "id") UUID id) {
        if(id != null) {
            return stockGroupService.findById(id);
        } else {
            throw new QueryException("not found");
        }
    }

    @GetMapping(value = "/find/all")
    @ResponseBody
    public List<StockGroupModel> findAll() {
        return stockGroupService.findAll();
    }

    @GetMapping(value = "/getAllItems")
    @ResponseBody
    public List<ItemModel> getAllItemInStockGroup(@RequestParam(value = "idGroup") UUID id) {
        if(id != null) {
            Optional<StockGroupModel> stock = findById(id);
            if(stock.isEmpty()) {
                throw new QueryException("stock not found");
            }
            return stock.get().getItems();
        }
        throw new NullPointerException("List<ItemModel> getAllItemInStockGroup(@RequestParam(value = \"idGroup\") UUID id == null)");
    }

}
