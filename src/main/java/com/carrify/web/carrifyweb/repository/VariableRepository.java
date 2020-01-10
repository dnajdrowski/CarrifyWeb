package com.carrify.web.carrifyweb.repository;


import com.carrify.web.carrifyweb.model.Variable.Variable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VariableRepository extends CrudRepository<Variable, Integer> {
    Optional<Variable> findByName(String name);
}
