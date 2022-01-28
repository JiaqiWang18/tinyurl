package com.jwang.shortener.controller;

import com.jwang.shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UrlController {

    @Autowired
    UrlService urlService;

    @GetMapping("/{hash}")
    public RedirectView redirectView(@PathVariable String hash){
        String originalUrl = urlService.retrieveOriginalUrl(hash);
        return new RedirectView(originalUrl);
    }

    @PostMapping("/url/generate")
    public Map<String, Object> createShortUrl(@RequestBody Map<String, String> reqBody){
        String originalUrl = reqBody.get("original");
        String shortenUrl = urlService.generateShortenUrl(originalUrl);
        Map<String, Object> res = new HashMap<>();
        res.put("data", shortenUrl);
        return res;
    }
}
