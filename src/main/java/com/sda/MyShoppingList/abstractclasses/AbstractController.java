package com.sda.MyShoppingList.abstractclasses;

import com.sda.MyShoppingList.exception.BusinessExeption;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public class AbstractController
        <ID_TYPE,
                MODEL extends AbstractModel<ID_TYPE>,
                REPOSITORY extends JpaRepository<MODEL, ID_TYPE>,
                SERVICE extends AbstractService<ID_TYPE, MODEL, REPOSITORY>> {

    protected SERVICE service;

    public AbstractController(SERVICE service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<MODEL> add(@RequestBody MODEL model) throws BusinessExeption {
        try{
            MODEL addedModel = service.add(model);
            return ResponseEntity.ok(addedModel);
        } catch (DataIntegrityViolationException e){
            throw new BusinessExeption(e);
        } catch (BusinessExeption exeption){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity delete(@PathVariable ID_TYPE id) throws EmptyResultDataAccessException {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MODEL>> get() {
        return ResponseEntity.ok(service.get());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<MODEL> get(@PathVariable ID_TYPE id) {
        Optional<MODEL> foundModel = service.get(id);
        return
                foundModel.isPresent() ?
                        ResponseEntity.ok(foundModel.get()) :
                        ResponseEntity.notFound().build();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<MODEL> update(@RequestBody MODEL model) {
        try {
            MODEL updatedModel = service.update(model);
            return ResponseEntity.ok(updatedModel);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
