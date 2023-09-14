package br.gov.es.conceicaodocastelo.stock.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.conceicaodocastelo.stock.dto.RequestRecordDto;
import br.gov.es.conceicaodocastelo.stock.models.Request;
import br.gov.es.conceicaodocastelo.stock.servicies.RequestServicie;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/request")
public class RequestController {
    
    final RequestServicie requestServicie;

    public RequestController(RequestServicie requestServicie) {
        this.requestServicie = requestServicie;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Request> createRequest(@RequestBody @Valid RequestRecordDto requestRecordDto) {
        Request request = new Request();
        BeanUtils.copyProperties(requestRecordDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestServicie.save(request));
    }
}
