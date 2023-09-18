package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.Request;
import br.gov.es.conceicaodocastelo.stock.repositories.RequestRepository;

@Service
public class RequestServicie {
    
    private final RequestRepository requestRepository;

    public RequestServicie(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request save(Request request) {
        if(request != null) {
            return requestRepository.save(request);
        }
        throw new NullPointerException("request = null");
    }

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
