package com.carrify.web.carrifyweb.controller;

import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomePageController {

    @PostMapping(value = "/hello", produces = "application/json")
    public String homePage(
            @RequestParam(required = false, defaultValue = "World") String name) {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("surname", name + " " + name);
        return object.toString();
    }
}
