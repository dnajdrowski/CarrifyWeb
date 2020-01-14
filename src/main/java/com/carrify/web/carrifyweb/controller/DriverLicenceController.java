package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.model.DriverLicence.DriverLicence;
import com.carrify.web.carrifyweb.request.DriverLicenceRequest;
import com.carrify.web.carrifyweb.service.DriverLicenceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    @GetMapping("/user/{id}/check")
    public ResponseEntity<Integer> checkIfUserSendDriverLicence(@PathVariable("id") String id) {
        return ResponseEntity.ok(driverLicenceService.checkIfUserSendDriverLicence(id));
    }

    @PostMapping("/user/{id}/upload")
    public ResponseEntity uploadOrUpdateDriverLicence(@PathVariable("id") String id, @RequestParam("front") MultipartFile frontImage,
                                           @RequestParam("reverse") MultipartFile reverseImage) {
        return ResponseEntity.ok(driverLicenceService.uploadDriverLicence(id, frontImage, reverseImage ));
    }

    @PostMapping("/user/{id}/verify")
    public ResponseEntity verifyDriverLicence(@PathVariable("id") String id, @Valid @RequestBody DriverLicenceRequest driverLicenceRequest,
                                              BindingResult results) {
        driverLicenceService.validateDriverLicenceRequest(results);
        return ResponseEntity.ok(driverLicenceService.verifyDriverLicence(id, driverLicenceRequest.getExpireDate(), results));
    }
}
