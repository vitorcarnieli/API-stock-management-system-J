package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.dto.OrderCreateRequestDto;
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
//Long institutionId, String nameOrder, String descriptionOrder, List<Long> itemsId, List<Integer> amounts
    public Order createOrder(OrderCreateRequestDto dto) throws Exception{
        Order order = new Order();
        this.save(order);
        if(dto.nameOrder() == null) {
            order.setName("Pedido " + order.getId());
        } else {

            order.setName(dto.nameOrder());
            order.setName(order.getName().substring(0, 1).toUpperCase().concat(order.getName().substring(1)));
            order.setObservation(dto.descriptionOrder());
        }

        Institution institution = institutionService.findById(Long.parseLong(dto.institutionId()));
        institution.addOrders(order);
        
        List<Item> itemsRequired = new ArrayList<>();
        dto.itemsId().forEach(id -> {
            try {
                itemsRequired.add(itemService.findById(Long.parseLong(id)));
            } catch (Exception e) {
               e.printStackTrace();
                return;
            }
        });

        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < dto.amounts().size() ; i++) {
            Request prematureRequest = new Request(order, itemsRequired.get(i), Integer.parseInt( dto.amounts().get(i)));
            requests.add(prematureRequest);
        }

        order.addRequests(requests);
        
        return this.save(order);
        
    }

    public List<Order> findByName(String name) {
        if (name != null) {
            List<Order> orders = this.findByNameO(name);
            return orders;
        } else {
            throw new NullPointerException(
                    "ResponseEntity<List<ItemModel>> findByName(@RequestParam(value = \"name\") String name  == null)");
        }
    }

}
