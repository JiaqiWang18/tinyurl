package com.jwang.shortener.controller;

import com.jwang.shortener.service.UrlService;

import com.jwang.shortener.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
public class UrlController {

    private final UrlService urlService;

    private final JwtTokenUtil jwtTokenUtil;

    public UrlController(UrlService urlService, JwtTokenUtil jwtTokenUtil){
        this.urlService = urlService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/{hash}")
    public RedirectView retrieveOriginal(@PathVariable String hash){
        String originalUrl = urlService.retrieveOriginalUrl(hash);
        if(originalUrl == null){
            log.info("has not found");
            return new RedirectView("https://tinyurl.jackywang.us/error/url-not-found");
        }
        return new RedirectView(originalUrl);
    }

    @PostMapping("/url/generate")
    public Map<String, Object> createShortUrl(@RequestBody Map<String, String> reqBody, @RequestHeader(name="Authorization") String requestTokenHeader){
        Map<String, Object> res = new HashMap<>();
        String username = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String token = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
                System.out.println(username);
            }  catch (ExpiredJwtException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "session timeout, please log in again");
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unknown error occurred, please try again");
            }
        }

        // validate original url
        if(!reqBody.containsKey("original")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "field original must be present!");
        }
        String originalUrl = reqBody.get("original").trim();
        if(!UrlValidator.getInstance().isValid(originalUrl)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "url is invalid!");
        }

        // validate date
        LocalDate expireDate;
        if(reqBody.containsKey("expireDate") && reqBody.get("expireDate") != null){
            expireDate = LocalDate.parse(reqBody.get("expireDate").substring(0,10));
            if(expireDate.isBefore(LocalDate.now())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "expiration date is invalid!");
            }
        }else{
            // default to one year
            expireDate = LocalDate.now().plusYears(1);
        }

        String shortenUrl;

        // validate alias
        if(reqBody.containsKey("alias") && reqBody.get("alias") != null && reqBody.get("alias").trim().length() > 0){
            String alias = reqBody.get("alias").trim();
            if(alias.length() < 6 || alias.length() > 15 || UrlValidator.getInstance().isValid(alias)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alias size must be between 6 and 15 and not a url");
            }
            if(alias.matches("^.*[^a-zA-Z0-9 ].*$")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alias must be alphanumeric");
            }
            shortenUrl = urlService.customizeShortenUrl(originalUrl, alias, expireDate, username);
            if(shortenUrl==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alias in use, try another one");
            }
        }else{
            shortenUrl = urlService.generateShortenUrl(originalUrl, expireDate, username);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("hash",shortenUrl);
        data.put("expireDate",expireDate);
        res.put("data", data);
        return res;
    }
}
