package br.gov.es.conceicaodocastelo.stock.servicies;

import br.gov.es.conceicaodocastelo.stock.models.ItemModel;
import br.gov.es.conceicaodocastelo.stock.repositories.ItemRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemModel save(ItemModel itemModel) {
        if (itemModel != null) {
            return itemRepository.save(itemModel);
        }
        throw new NullPointerException("ItemModel save(ItemModel itemModel == null)");
    }

    public void decrementAmountItem(Double amount, ItemModel itemModel) {
        if (amount != null && itemModel != null) {
            double currentAmount = itemModel.getAmount() - amount;
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

    public void incrementAmountItem(Double amount, ItemModel itemModel) {
        if (amount != null && amount >= 0.01 && itemModel != null) {
            itemModel.setAmount(itemModel.getAmount() + amount);
            this.save(itemModel);
        } else {
            throw new NullPointerException("amount or itemModel or both is null or amount < 0.01");
        }
    }

    public ResponseEntity<List<ItemModel>> findByName(@RequestParam(value = "name") String name) {
        if (name != null) {
            List<ItemModel> items = itemRepository.findByNome(name);
            return new ResponseEntity<List<ItemModel>>(items, HttpStatus.OK);
        } else {
            throw new NullPointerException("ResponseEntity<List<ItemModel>> findByName(@RequestParam(value = \"name\") String name  == null)");
        }
    }

    @Transactional
    public ResponseEntity<ItemModel> addChanges(UUID id, Integer change) {
        if(id != null & change != null) {
            ItemModel item = itemRepository.findById(id).get();
            item.addChanges(change);
            itemRepository.save(item);
            System.out.println("salvou");
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } else {
            throw new NullPointerException("null pointer");
        }
    }

    public ResponseEntity<Optional<ItemModel>> findById(UUID id) {
        if(id != null) {
            Optional<ItemModel> optItemModel = itemRepository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(optItemModel);
        } else {
            throw new NullPointerException("null");
        }
    }

}
