package br.gov.es.conceicaodocastelo.stock.servicies;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.servicies.generic.GenericServiceImp;
import br.gov.es.conceicaodocastelo.stock.servicies.interfaces.ItemInterface;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ItemService extends GenericServiceImp<Item> implements ItemInterface{


    public void decrementAmountItem(Integer amount, Item itemModel) {
        if (amount != null && itemModel != null) {
            int currentAmount = itemModel.getAmount() - amount;
            if (currentAmount >= 0) {
                itemModel.setAmount(currentAmount);
                this.save(itemModel);
            } else {
                throw new RuntimeException("itemModel.getAmount() - amount < 0");
            }
        } else {
            throw new NullPointerException("amount or itemModel or both is null");
        }
    }

    public void incrementAmountItem(Integer amount, Item itemModel) {
        if (amount != null && amount >= 0.01 && itemModel != null) {
            itemModel.setAmount(itemModel.getAmount() + amount);
            this.save(itemModel);
        } else {
            throw new NullPointerException("amount or itemModel or both is null or amount < 0.01");
        }
    }

    public Item changeAmountItem(Integer amount, Item itemModel) {
        if (amount != null && amount > 0) {
            itemModel.setAmount(amount);
            this.save(itemModel);
            return itemModel;
        } else {
            throw new NullPointerException("Invalid argument for amount or itemModel");
        }
    }

    public List<Item> findByName(String name) {
        if (name != null) {
            List<Item> items = this.findByNameI(name);
            return items;
        } else {
            throw new NullPointerException(
                    "ResponseEntity<List<ItemModel>> findByName(@RequestParam(value = \"name\") String name  == null)");
        }
    }

    public Item addChanges(Long id, Integer amount) {
        if (id != null & amount != null) {
            Item item;
            try {
                item = this.findById(id);
                item.increaseOrDecreaseAmount(amount);
                this.save(item);
                return item;
            } catch (Exception e) {
                return null;
            }
        } else {
            throw new NullPointerException("null pointer");
        }
    }

}
