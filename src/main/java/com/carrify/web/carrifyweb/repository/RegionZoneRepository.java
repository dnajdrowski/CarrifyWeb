package com.carrify.web.carrifyweb.repository;

import com.carrify.web.carrifyweb.model.RegionZone.RegionZone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionZoneRepository extends CrudRepository<RegionZone, Integer> {
}
