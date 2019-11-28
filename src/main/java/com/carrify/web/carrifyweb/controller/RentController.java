package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.repository.Rent.RentDTO;
import com.carrify.web.carrifyweb.request.RentRequest;
import com.carrify.web.carrifyweb.service.RentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/rents")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public ResponseEntity<List<RentDTO>> showAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDTO> showRent(@PathVariable("id") String rentId) {
        return ResponseEntity.ok(rentService.getRentById(rentId));
    }

    @GetMapping("/{id}/finish")
    public ResponseEntity<RentDTO> finishRent(@PathVariable("id") String rentId) {
        return ResponseEntity.ok(rentService.finishRent(rentId));
    }

    @GetMapping("/car/{id}/all")
    public ResponseEntity<List<RentDTO>> showAllCarRents(@PathVariable("id") String carId) {
        return ResponseEntity.ok(rentService.getAllCarRents(carId));
    }

    @GetMapping("user/{id}/all")
    public ResponseEntity<List<RentDTO>> showAllUserRents(@PathVariable("id") String userId) {
        return ResponseEntity.ok(rentService.getAllUserRents(userId));
    }

    @GetMapping("user/{id}/active")
    public ResponseEntity<RentDTO> showActiveUserRent(@PathVariable("id") String userId) {
        return ResponseEntity.ok(rentService.getUserActiveRent(userId));
    }

    @PostMapping("/new-rent")
    public ResponseEntity<RentDTO> addNewRent(@RequestBody RentRequest rentRequest) {
        return ResponseEntity.ok(rentService.startRent(rentRequest.getUserId(), rentRequest.getCarId()));
    }

}
