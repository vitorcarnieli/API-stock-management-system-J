package br.gov.es.conceicaodocastelo.stock.servicies.generic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;

import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.models.generic.BaseEntity;
import br.gov.es.conceicaodocastelo.stock.repositories.generic.GenericRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@NoRepositoryBean
public class GenericServiceImp<T extends BaseEntity> implements GenericService<T, Long> {

    @Autowired
    private GenericRepository<T> genericRepository;

    public T save(T entity) {
        try {
            return genericRepository.save(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<T> saveAll(List<T> entitys) {
        try {
            return genericRepository.saveAll(entitys);
        } catch (Exception e) {
            throw e;
        }
    }

    public T findById(Long id) throws Exception {
        try {
            Optional<T> entity = genericRepository.findById(id);
            if (entity.isPresent()) {
                return entity.get();
            }
            throw new Exception("Entity not found by id");
        } catch (Exception e) {
            throw e;
        }
    }

    public List<T> findAll() {
        try {
            return genericRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public long count() {
        try {
            return genericRepository.count();
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(T entity) {
        try {
            genericRepository.delete(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteById(Long id) {
        try {
            genericRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteAll(List<T> entitys) {
        try {
            genericRepository.deleteAll(entitys);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Item> findByNameI(String name) {
        try {
            return genericRepository.findByNameI(name);
        } catch (Exception e) {
            throw e;
        }
    }

    
    public List<StockGroup> findByNameS(String name) {
        try {
            return genericRepository.findByNameS(name);
        } catch (Exception e) {
            throw e;
        }
    }
}
