package com.jwang.shortener.controller;

import com.jwang.shortener.model.UrlEntity;
import com.jwang.shortener.service.UrlService;
import com.jwang.shortener.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/urls")
    public Map<String, Object> getUserUrls(@RequestHeader(name="Authorization") String requestTokenHeader){
        String username;
        Map<String, Object> res = new HashMap<>();
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String token = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
            }  catch (ExpiredJwtException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "session timeout, please log in again");
            }
        }else{
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no token found");
        }

        List<UrlEntity> userUrls = urlService.getUserUrls(username);
        res.put("data", userUrls);
        return res;
    }
}
