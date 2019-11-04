package com.carrify.web.carrifyweb.controller;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomePageController {

    @GetMapping({"", "/", "/hello"})
    public ResponseEntity<String> homePage(@RequestParam(required = false, defaultValue = "World") String name) {
        JSONObject object = new JSONObject();
        object.put("name", name);
        return new ResponseEntity<>(object.toString(), HttpStatus.OK);
    }
}
