package br.gov.es.conceicaodocastelo.stock.servicies;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.servicies.generic.GenericServiceImp;
import br.gov.es.conceicaodocastelo.stock.servicies.interfaces.StockGroupInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockGroupService extends GenericServiceImp<StockGroup> implements StockGroupInterface{

    @Autowired
    ItemService itemService;

    public List<StockGroup> findByName(String name) {
        return this.findByNameS(name);
    }

    public Optional<Item> findItemInStockGroupById(StockGroup stockGroupModel, Long id) {
        if (stockGroupModel != null) {
            Optional<Item> item = this.findAllItemsInStockGroup(stockGroupModel).stream()
                    .filter(i -> i.getId().equals(id)).findFirst();
            if (item.isPresent()) {
                return item;
            } else {
                throw new RuntimeException("item not funded");
            }
        } else {
            throw new NullPointerException("null arg");
        }
    }

    public StockGroup findStockGroupByItem(Long itemId) {
        try {
            return itemService.findById(itemId).getStockGroup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Item> findAllItemsInStockGroup(StockGroup stockGroupModel) {
            List<Item> items = stockGroupModel.getItems();
            if (items.isEmpty()) {
                throw new RuntimeException("empty list");
            } else {
                return items;
            }
    }

}
