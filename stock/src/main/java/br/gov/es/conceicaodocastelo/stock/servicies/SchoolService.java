package br.gov.es.conceicaodocastelo.stock.servicies;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.models.School;
import br.gov.es.conceicaodocastelo.stock.repositories.SchoolRepository;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School save(School school) {
        return schoolRepository.save(school);
    }

    public List<School> findAll() {
        List<School> schools = schoolRepository.findAll();
        if(schools.isEmpty()) {
            throw new RuntimeException("schools is empty");
        } else {
            return schools;
        }
    }

    public School findById(String id) {
        Optional<School> school = schoolRepository.findById(UUID.fromString(id));
        if(school.isPresent()) {
            return school.get();
        } else {
            throw new RuntimeException("not found school by id");
        }
    }


    
}
