package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.Request;
import br.gov.es.conceicaodocastelo.stock.servicies.generic.GenericServiceImp;
import br.gov.es.conceicaodocastelo.stock.servicies.interfaces.RequestInterface;

@Service
public class RequestServicie extends GenericServiceImp<Request> implements RequestInterface{

    public Request createRequest(Request request) {
        if(request.getRequiredAmount() - request.getItem().getAmount() < 0) {
            throw new RuntimeException("quantidade requerida maior que quantidade disponivel");
        }
        return this.save(request);
    }

    public Request changeItem(Request request, Item item) {
        request.setItem(item);
        return this.save(request);
    }

    public Request changeAmount(Request request, Integer amount) {
        request.setRequiredAmount(amount);
        return this.save(request);
    }

    public List<Request> getAllRequestsToItem(Item item) {
        return item.getRequests();
    }

    public Item getItemToRequest(Request request) {
        return request.getItem();
    }

    

    
}
