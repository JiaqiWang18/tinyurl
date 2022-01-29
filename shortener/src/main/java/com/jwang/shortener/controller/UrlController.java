package com.jwang.shortener.controller;

import com.jwang.shortener.service.UrlService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/url")
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @GetMapping("/retrieve/{hash}")
    public Map<String, Object> retrieveOriginal(@PathVariable String hash){
        String originalUrl = urlService.retrieveOriginalUrl(hash);
        Map<String, Object> res = new HashMap<>();
        if(originalUrl == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "url not found");
        }
        res.put("data", originalUrl);
        return res;
    }

    @PostMapping("/generate")
    public Map<String, Object> createShortUrl(@RequestBody Map<String, String> reqBody){
        Map<String, Object> res = new HashMap<>();

        if(!reqBody.containsKey("original")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "field original must be present!");
        }
        String originalUrl = reqBody.get("original");
        String shortenUrl;

        if(reqBody.containsKey("hash")){
            String hash = reqBody.get("hash");
            if(hash.isEmpty() || hash.length() > 10){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customized url size is invalid!");
            }
            shortenUrl = urlService.customizeShortenUrl(originalUrl, hash);
            if(shortenUrl==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "url in use, try another one");
            }
        }else{
            shortenUrl = urlService.generateShortenUrl(originalUrl);
        }
        res.put("data", shortenUrl);
        return res;
    }
}
