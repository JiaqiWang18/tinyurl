package com.jwang.shortener.controller;

import com.jwang.shortener.service.UrlService;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @GetMapping("/{hash}")
    public RedirectView retrieveOriginal(@PathVariable String hash){
        String originalUrl = urlService.retrieveOriginalUrl(hash);
        if(originalUrl == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "url not found");
        }
        return new RedirectView(originalUrl);
    }

    @PostMapping("/url/generate")
    public Map<String, Object> createShortUrl(@RequestBody Map<String, String> reqBody){
        Map<String, Object> res = new HashMap<>();

        if(!reqBody.containsKey("original")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "field original must be present!");
        }
        String originalUrl = reqBody.get("original").trim();
        if(!UrlValidator.getInstance().isValid(originalUrl)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "url is invalid!");
        }
        String shortenUrl;

        if(reqBody.containsKey("alias") && reqBody.get("alias") != null && reqBody.get("alias").trim().length() > 0){
            String alias = reqBody.get("alias").trim();
            if(alias.length() < 6 || alias.length() > 15 || UrlValidator.getInstance().isValid(alias)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alias size must be between 6 and 15 and not a url");
            }
            if(alias.matches("^.*[^a-zA-Z0-9 ].*$")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alias must be alphanumeric");
            }
            shortenUrl = urlService.customizeShortenUrl(originalUrl, alias);
            if(shortenUrl==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alias in use, try another one");
            }
        }else{
            shortenUrl = urlService.generateShortenUrl(originalUrl);
        }
        res.put("data", shortenUrl);
        return res;
    }
}
