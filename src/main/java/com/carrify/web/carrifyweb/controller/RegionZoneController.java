package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.model.RegionZone.RegionZone;
import com.carrify.web.carrifyweb.response.ApiErrorResponse;
import com.carrify.web.carrifyweb.service.RegionZoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
@Api(tags = "Region Zone")
@RequestMapping("api/region-zones")
public class RegionZoneController {

    private final RegionZoneService regionZoneService;

    public RegionZoneController(RegionZoneService regionZoneService) {
        this.regionZoneService = regionZoneService;
    }

    @ApiOperation(value = "Get region Zone details", response = RegionZone.class,
            produces = APPLICATION_JSON_VALUE)

    @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY004_CODE + "\n" + "msg: " + CARRIFY004_MSG,
            response = ApiErrorResponse.class)
    @GetMapping("/{id}")
    public ResponseEntity regionZoneDetails(@PathVariable("id") String regionZoneId) {
            return ResponseEntity.ok(regionZoneService.getRegionZone(regionZoneId));
    }

}
