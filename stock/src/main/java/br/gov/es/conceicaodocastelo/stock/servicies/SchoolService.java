package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.List;
import java.util.Optional;

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

    public void save(School school) {
        schoolRepository.save(school);
    }

    public List<School> findAll() {
        List<School> schools = schoolRepository.findAll();
        if(schools.isEmpty()) {
            throw new RuntimeException("schools is empty");
        } else {
            return schools;
        }
    }

    public School createRequestIntoSchool(School school, Request request) {
        if(school != null && request != null) {
            Optional<School> optSchool = schoolRepository.findById(school.getId());
            if (optSchool.isPresent()) {
                school = optSchool.get();
                school.addRequest(request);
                return schoolRepository.save(school);
            } else {
                throw new RuntimeException("School not found");
            }
        } else {
            throw new NullPointerException("Null args");
        }
    }

    public List<Request> getAllRequestsIntoSchool(School school) {
        return school.getRequests();
    }

    public School getSchoolIntoRequest(Request request) {
        return request.getRequesterSchool();
    }

    public void deleteRequest(School school, Request request) {
        if(school != null && request != null) {
            Optional<School> optSchool = schoolRepository.findById(school.getId());
            if (optSchool.isPresent()) {
                school = optSchool.get();
                school.deleteRequest(request);
                schoolRepository.save(school);
            } else {
                throw new RuntimeException("School not found");
            }
        } else {
            throw new NullPointerException("Null args");
        }
    }

    
}
