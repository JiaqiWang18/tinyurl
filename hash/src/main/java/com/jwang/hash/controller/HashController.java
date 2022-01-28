package com.jwang.hash.controller;

import com.jwang.hash.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hash")
public class HashController {

    @Autowired
    RetrieveService retrieveService;

    @GetMapping("/retrieve")
    public Map<String, Object> retrieve(){
        Map<String, Object> result = new HashMap<>();
        String hash = retrieveService.retrieveOne();
        result.put("data", hash);
        return result;
    }

    @GetMapping("/")
    public String test(){
        return "hash service";
    }
}
