package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.model.Rent.RentDTO;
import com.carrify.web.carrifyweb.request.RentRequest;
import com.carrify.web.carrifyweb.response.ApiErrorResponse;
import com.carrify.web.carrifyweb.service.RentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@Api(tags = "Rents")
@RequestMapping("/api/rents")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }


    @ApiOperation(value = "View a list of all rents", response = RentDTO.class, responseContainer = "List",
            produces = APPLICATION_JSON_VALUE)

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY012_CODE + "\n" + "msg: " + CARRIFY012_MSG,
                    response = ApiErrorResponse.class)
    })
    @GetMapping
    public ResponseEntity<List<RentDTO>> showAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }


    @ApiOperation(value = "Get rent details", response = RentDTO.class, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY014_CODE + "\n" + "msg: " + CARRIFY014_MSG,
                    response = ApiErrorResponse.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentDTO> showRent(@PathVariable("id") String rentId) {
        return ResponseEntity.ok(rentService.getRentById(rentId));
    }

    @ApiOperation(value = "Finish rent", response = RentDTO.class, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY014_CODE + "\n" + "msg: " + CARRIFY014_MSG + "\n"
                    + "code: " + CARRIFY013_CODE + "\n" + "msg: " + CARRIFY013_MSG, response = ApiErrorResponse.class),
            @ApiResponse(code = 400, message = "Errors:\ncode: " + CARRIFY015_CODE + "\n" + "msg: " + CARRIFY015_MSG,
                    response = ApiErrorResponse.class)
    })
    @GetMapping("/{id}/finish")
    public ResponseEntity<RentDTO> finishRent(@PathVariable("id") String rentId) {
        return ResponseEntity.ok(rentService.finishRent(rentId));
    }

    @ApiOperation(value = "View a list of car rents", response = RentDTO.class, responseContainer = "List",
            produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY012_CODE + "\n" + "msg: " + CARRIFY012_MSG + "\n"
                    + "code: " + CARRIFY013_CODE + "\n" + "msg: " + CARRIFY013_MSG, response = ApiErrorResponse.class)
    })
    @GetMapping("/car/{id}/all")
    public ResponseEntity<List<RentDTO>> showAllCarRents(@PathVariable("id") String carId) {
        return ResponseEntity.ok(rentService.getAllCarRents(carId));
    }

    @ApiOperation(value = "View a list of user rents", response = RentDTO.class, responseContainer = "List",
            produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY012_CODE + "\n" + "msg: " + CARRIFY012_MSG + "\n"
                    + "code: " + CARRIFY009_CODE + "\n" + "msg: " + CARRIFY009_MSG, response = ApiErrorResponse.class)
    })
    @GetMapping("user/{id}/all")
    public ResponseEntity<List<RentDTO>> showAllUserRents(@PathVariable("id") String userId) {
        return ResponseEntity.ok(rentService.getAllUserRents(userId));
    }

    @ApiOperation(value = "Show user active rent", response = RentDTO.class, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY012_CODE + "\n" + "msg: " + CARRIFY012_MSG + "\n"
                    + "code: " + CARRIFY009_CODE + "\n" + "msg: " + CARRIFY009_MSG, response = ApiErrorResponse.class)
    })
    @GetMapping("user/{id}/active")
    public ResponseEntity<RentDTO> showActiveUserRent(@PathVariable("id") String userId) {
        return ResponseEntity.ok(rentService.getUserActiveRent(userId));
    }

    @ApiOperation(value = "Add new rent", response = RentDTO.class, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY013_CODE + "\n" + "msg: " + CARRIFY013_MSG + "\n"
                    + "code: " + CARRIFY009_CODE + "\n" + "msg: " + CARRIFY009_MSG, response = ApiErrorResponse.class),
            @ApiResponse(code = 400, message = "Errors:\ncode: " + CARRIFY016_CODE + "\n" + "msg: " + CARRIFY016_MSG + "\n"
                    + "code: " + CARRIFY017_CODE + "\n" + "msg: " + CARRIFY017_MSG, response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Errors:\ncode: " + "\n" + CARRIFY_INTERNAL_CODE + "\n" + "msg: " + CARRIFY_INTERNAL_MSG,
                    response = ApiErrorResponse.class)

    })
    @PostMapping("/new-rent")
    public ResponseEntity<RentDTO> addNewRent(@RequestBody RentRequest rentRequest) {
        return ResponseEntity.ok(rentService.startRent(rentRequest.getUserId(), rentRequest.getCarId()));
    }

}
