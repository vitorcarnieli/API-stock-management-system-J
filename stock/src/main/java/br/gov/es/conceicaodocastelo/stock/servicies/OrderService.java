package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.models.Institution;
import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.models.Request;
import br.gov.es.conceicaodocastelo.stock.servicies.generic.GenericServiceImp;
import br.gov.es.conceicaodocastelo.stock.servicies.interfaces.OrderInterface;

@Service
public class OrderService extends GenericServiceImp<Order> implements OrderInterface{

    @Autowired
    InstitutionService institutionService;

    @Autowired
    ItemService itemService;

    public Order createOrder(Long institutionId, String nameOrder, String descriptionOrder, List<Long> itemsId, List<Integer> amounts) throws RuntimeException{
        try {
            Order order = new Order();
            order.setName(nameOrder);
            order.setObservation(descriptionOrder);
            Institution institution = institutionService.findById(institutionId);
            List<Item> items = new ArrayList<>();
            itemsId.forEach(id -> {
                try {
                    items.add(itemService.findById(id));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            for (int i = 0; i < items.size(); i++) {
                Request request = new Request();
                request.setOrder(order);
                request.setRequiredAmount(amounts.get(i));
                request.setItem(items.get(i));
                System.out.println(order.getRequests().toString());
            }
            institution.addOrders(order);
            return this.save(order);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

}
