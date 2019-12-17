package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.model.DriverLicence.DriverLicence;
import com.carrify.web.carrifyweb.service.DriverLicenceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Driver Licence")
@RequestMapping("/api/driver-licences")
public class DriverLicenceController {

    private final DriverLicenceService driverLicenceService;

    public DriverLicenceController(DriverLicenceService driverLicenceService) {
        this.driverLicenceService = driverLicenceService;
    }

    @GetMapping
    public ResponseEntity<List<DriverLicence>> showAllDriverLicences() {
        return ResponseEntity.ok(driverLicenceService.getAllDriverLicences());
    }

    @PostMapping("/user/{id}/uploadFront")
    public ResponseEntity uploadFrontImage(@PathVariable("id") String userId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(driverLicenceService.uploadFrontDriverLicenceImage(userId, file));
    }
}
