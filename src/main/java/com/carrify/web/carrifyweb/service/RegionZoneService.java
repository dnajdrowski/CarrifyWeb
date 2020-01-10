package com.carrify.web.carrifyweb.service;


import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.RegionZone.RegionZone;
import com.carrify.web.carrifyweb.model.RegionZone.RegionZoneDTO;
import com.carrify.web.carrifyweb.repository.RegionZoneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY004_CODE;
import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY004_MSG;

@Service
public class RegionZoneService {

    private final RegionZoneRepository regionZoneRepository;

    public RegionZoneService(RegionZoneRepository regionZoneRepository) {
        this.regionZoneRepository = regionZoneRepository;
    }

    public RegionZoneDTO getRegionZone(String regionZoneId) {
        int regionZoneIdInteger;
        try {
            regionZoneIdInteger = Integer.parseInt(regionZoneId);
        } catch (NumberFormatException e) {
            regionZoneIdInteger = -1;
        }

        Optional<RegionZone> regionZone = regionZoneRepository.findById(regionZoneIdInteger);
        if(regionZone.isPresent()) {
            return new RegionZoneDTO(regionZone.get());
        } else {
            throw new ApiNotFoundException(CARRIFY004_MSG ,CARRIFY004_CODE);
        }
    }

}
