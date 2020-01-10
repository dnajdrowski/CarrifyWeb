package com.carrify.web.carrifyweb.repository;


import com.carrify.web.carrifyweb.model.RegionZoneCoords.RegionZoneCoords;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionZoneCoordsRepository extends CrudRepository<RegionZoneCoords, Integer> {
}
