package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.repository.RegionZone.RegionZone;
import com.carrify.web.carrifyweb.repository.RegionZone.RegionZoneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegionZoneService {

    private final RegionZoneRepository regionZoneRepository;

    public RegionZoneService(RegionZoneRepository regionZoneRepository) {
        this.regionZoneRepository = regionZoneRepository;
    }

    public Optional<RegionZone> getRegionZone(String regionZoneId) {
        Integer regionZoneIdInteger;
        try {
            regionZoneIdInteger = Integer.parseInt(regionZoneId);
        } catch (NumberFormatException e) {
            regionZoneIdInteger = -1;
        }
        return regionZoneRepository.findById(regionZoneIdInteger);
    }

}
