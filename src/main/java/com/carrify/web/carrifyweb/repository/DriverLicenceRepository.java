package com.carrify.web.carrifyweb.repository;


import com.carrify.web.carrifyweb.model.DriverLicence.DriverLicence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverLicenceRepository extends CrudRepository<DriverLicence, Integer> {
}
