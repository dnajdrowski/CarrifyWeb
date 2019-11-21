package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.repository.RegionZone.RegionZone;
import com.carrify.web.carrifyweb.service.RegionZoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY004_CODE;
import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY004_MSG;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
@Api(tags = "Region Zone")
@RequestMapping("/region-zones")
public class RegionZoneController {

    private final RegionZoneService regionZoneService;

    public RegionZoneController(RegionZoneService regionZoneService) {
        this.regionZoneService = regionZoneService;
    }

    @ApiOperation(value = "Get Region Zone details", response = RegionZone.class,
            produces = APPLICATION_JSON_VALUE)
    @GetMapping("/{id}")
    public ResponseEntity regionZoneDetails(@PathVariable("id") String regionZoneId) {
        Optional<RegionZone> regionZoneOptional = regionZoneService.getRegionZone(regionZoneId);
        if(regionZoneOptional.isPresent()) {
            return ResponseEntity.ok(regionZoneOptional.get());
        } else {
            throw new ApiNotFoundException(CARRIFY004_MSG ,CARRIFY004_CODE);
        }
    }

}
