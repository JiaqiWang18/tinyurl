package com.jwang.shortener.service;

import com.jwang.shortener.model.UrlEntity;
import com.jwang.shortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService {
    @Autowired
    UrlRepository urlRepository;

    @Autowired
    HashFeignService hashFeignService;

    Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Transactional
    public String generateShortenUrl(String originalUrl){
        logger.info("Start generateShortenUrl");
        String hash = (String) hashFeignService.retrieve().get("data");
        logger.info("Retrieved a hash from hash service: "+hash);
        urlRepository.save(new UrlEntity(hash, originalUrl));
        logger.info("Saved hash and original to db");
        return hash;
    }

    @Transactional
    public String retrieveOriginalUrl(String hash){
        logger.info("Start retrieveOriginalUrl");
        String originalUrl = urlRepository.findById(hash).get().getOriginalUrl();
        logger.info("Found original Url " + originalUrl);
        return originalUrl;
    }
}
