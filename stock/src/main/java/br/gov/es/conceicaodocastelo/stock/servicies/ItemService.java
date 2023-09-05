package br.gov.es.conceicaodocastelo.stock.servicies;

import br.gov.es.conceicaodocastelo.stock.models.ItemModel;
import br.gov.es.conceicaodocastelo.stock.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemModel save(ItemModel itemModel) {
        if(itemModel != null) {
            return itemRepository.save(itemModel);
        }
        throw new NullPointerException("ItemModel save(ItemModel itemModel == null)");
    }

}
