package com.carrify.web.carrifyweb.controller;

import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomePageController {

    @PostMapping(value = "/hello", produces = "application/json")
    public String helloPage(
            @RequestParam(required = false, defaultValue = "World") String name) {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("surname", name + " " + name);
        return object.toString();
    }

    @GetMapping({"", "/"})
    public String homePage() {
        JSONObject object = new JSONObject();
        object.put("appname", "carrify-web");
        return object.toString();
    }
}
