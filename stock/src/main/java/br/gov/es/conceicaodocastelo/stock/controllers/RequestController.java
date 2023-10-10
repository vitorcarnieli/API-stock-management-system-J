package br.gov.es.conceicaodocastelo.stock.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.conceicaodocastelo.stock.controllers.generic.GenericControllerImp;
import br.gov.es.conceicaodocastelo.stock.controllers.interfaces.RequestInterface;
import br.gov.es.conceicaodocastelo.stock.models.Request;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/request")
public class RequestController extends GenericControllerImp<Request> implements RequestInterface{
    

}
