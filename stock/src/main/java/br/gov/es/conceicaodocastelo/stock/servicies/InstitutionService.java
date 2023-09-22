package br.gov.es.conceicaodocastelo.stock.servicies;

import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.models.Institution;
import br.gov.es.conceicaodocastelo.stock.servicies.generic.GenericServiceImp;
import br.gov.es.conceicaodocastelo.stock.servicies.interfaces.InstitutionIf;

@Service
public class InstitutionService extends GenericServiceImp<Institution> implements InstitutionIf{


}
