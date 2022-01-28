package com.jwang.shortener.controller;

import com.jwang.shortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @GetMapping("/{hash}")
    public RedirectView redirectView(@PathVariable String hash){
        String originalUrl = urlService.retrieveOriginalUrl(hash);
        if(originalUrl == null){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "404 url not found"
            );
        }
        return new RedirectView(originalUrl);
    }

    @ResponseBody
    @PostMapping("/url/generate")
    public Map<String, Object> createShortUrl(@RequestBody Map<String, String> reqBody){
        String originalUrl = reqBody.get("original");
        String shortenUrl = urlService.generateShortenUrl(originalUrl);
        Map<String, Object> res = new HashMap<>();
        res.put("data", shortenUrl);
        return res;
    }
}
