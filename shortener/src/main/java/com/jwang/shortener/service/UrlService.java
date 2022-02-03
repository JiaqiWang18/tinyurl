package com.jwang.shortener.service;

import com.jwang.shortener.model.UrlEntity;
import com.jwang.shortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UrlService {


    private final UrlRepository urlRepository;

    private final HashFeignService hashFeignService;

    public UrlService(UrlRepository urlRepository, HashFeignService hashFeignService) {
        this.urlRepository = urlRepository;
        this.hashFeignService = hashFeignService;
    }

    @Transactional
    public String generateShortenUrl(String originalUrl, LocalDate expireDate, String username){
        log.info("Start generateShortenUrl");
        String hash = (String) hashFeignService.retrieve().get("data");
        log.info("Retrieved a hash from hash service: "+hash);
        urlRepository.save(new UrlEntity(hash, originalUrl, expireDate, username));
        log.info("Saved hash and original to db");
        return hash;
    }

    @Transactional
    public String customizeShortenUrl(String originalUrl, String hash, LocalDate expireDate, String username){
        log.info("Start customizeShortenUrl");
        // if hash exists, it has been usedn
        if(urlRepository.existsById(hash)){
            return null;
        }else{
            urlRepository.save(new UrlEntity(hash, originalUrl, expireDate, username));
            // call hash service to mark this url as used and remove it if it has been auto generated
            hashFeignService.markHashAsUsed(hash);
            return hash;
        }
    }

    @Transactional
    public String retrieveOriginalUrl(String hash){
        log.info("Start retrieveOriginalUrl");
        Optional<UrlEntity> urlEntity = urlRepository.findById(hash);
        if(urlEntity.isPresent()){
            log.info("Found original Url");
            if(urlEntity.get().getExpireDate().isBefore(LocalDate.now())){
                log.info("Url expired");
                urlRepository.deleteById(urlEntity.get().getHash());
                hashFeignService.markHashAsUnUsed(urlEntity.get().getHash());
                return null;
            }
            return urlEntity.get().getOriginalUrl();
        }
        return null;
    }

    @Transactional
    public List<UrlEntity> getUserUrls(String username){
        log.info("Start getUserUrls for: " + username);
        return urlRepository.findUrlEntitiesByUsername(username);
    }
}
