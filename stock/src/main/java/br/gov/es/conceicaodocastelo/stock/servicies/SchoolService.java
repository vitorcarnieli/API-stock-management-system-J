package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.models.Request;
import br.gov.es.conceicaodocastelo.stock.models.School;
import br.gov.es.conceicaodocastelo.stock.repositories.SchoolRepository;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School save(School school) {
        if(school != null) {
            return schoolRepository.save(school);
        }
        throw new NullPointerException("school == null");
    }

    public List<Request> getAllRequestsIntoSchool(School school) {
        return school.getRequests();
    }

    public School getSchoolIntoRequest(Request request) {
        return request.getRequesterSchool();
    }

    
}
