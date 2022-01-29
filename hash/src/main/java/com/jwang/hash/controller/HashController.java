package com.jwang.hash.controller;

import com.jwang.hash.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{hash}")
    public Map<String, Object> markHashAsUsed(@PathVariable("hash") String hash){
        Map<String, Object> result = new HashMap<>();
        retrieveService.markHashAsUsed(hash);
        result.put("status",200);
        return result;
    }

    @GetMapping("/")
    public String test(){
        return "hash service";
    }
}
