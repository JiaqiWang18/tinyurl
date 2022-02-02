package com.jwang.shortener.controller;

import com.jwang.shortener.model.UrlEntity;
import com.jwang.shortener.service.UrlService;
import com.jwang.shortener.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserUrlController {
    private final UrlService urlService;

    private final JwtTokenUtil jwtTokenUtil;

    public UserUrlController(UrlService urlService, JwtTokenUtil jwtTokenUtil) {
        this.urlService = urlService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/urls")
    public Map<String, Object> getUserUrls(@RequestHeader(name="Authorization") String requestTokenHeader){
        System.out.println(requestTokenHeader);
        String username;
        Map<String, Object> res = new HashMap<>();
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String token = requestTokenHeader.substring(7);
            if(jwtTokenUtil.isTokenExpired(token)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "session timeout, please log in again");
            }
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
            }  catch (ExpiredJwtException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "session timeout, please log in again");
            }
        }else{
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no token found");
        }

        List<UrlEntity> userUrls = urlService.getUserUrls(username);
        res.put("data", userUrls);
        System.out.println(res);
        return res;
    }
}
