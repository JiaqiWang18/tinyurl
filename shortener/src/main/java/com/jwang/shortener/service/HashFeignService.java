package com.jwang.shortener.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Service
@FeignClient(value = "hash-depl")
public interface HashFeignService {
    @GetMapping("/hash/retrieve")
    Map<String, Object> retrieve();
}
