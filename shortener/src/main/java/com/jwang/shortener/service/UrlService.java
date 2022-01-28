package com.jwang.shortener.service;

import com.jwang.shortener.model.UrlEntity;
import com.jwang.shortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UrlService {
    @Autowired
    UrlRepository urlRepository;

    @Autowired
    HashFeignService hashFeignService;

    @Transactional
    public String generateShortenUrl(String originalUrl){
        log.info("Start generateShortenUrl");
        String hash = (String) hashFeignService.retrieve().get("data");
        log.info("Retrieved a hash from hash service: "+hash);
        urlRepository.save(new UrlEntity(hash, originalUrl));
        log.info("Saved hash and original to db");
        return hash;
    }

    @Transactional
    public String retrieveOriginalUrl(String hash){
        log.info("Start retrieveOriginalUrl");
        Optional<UrlEntity> urlEntity = urlRepository.findById(hash);
        if(urlEntity.isPresent()){
            log.info("Found original Url");
            return urlEntity.get().getOriginalUrl();
        }
        return null;
    }
}
