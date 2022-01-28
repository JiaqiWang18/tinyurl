package com.jwang.hash.service;

import com.jwang.hash.model.HashEntity;
import com.jwang.hash.repository.HashRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GeneratorService {

    @Autowired
    HashRepository hashRepository;

    Logger logger = LoggerFactory.getLogger(GeneratorService.class);

    /**
     * Ingest n hashes to db
     */
    public void ingestHashes(Integer n) {
        logger.info("hash ingestion start");
        for (int i = 0; i < n; i++) {
            String hash = generateOne();
            boolean exists = hashRepository.existsById(hash);
            if(!exists){
                hashRepository.save(new HashEntity(hash));
                logger.info("ingested " + hash);
            }
        }
    }

    /**
     * Generate one six digit hash with 0-9 and a-z
     */
    private String generateOne(){
        int length = 6;
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            if(random.nextBoolean()){
                int randInt = random.nextInt(10);
                builder.append(randInt);
            }
            else{
                int randChar = 'a' + (int)
                        (random.nextFloat() * ('z' - 'a' + 1));
                builder.append((char) randChar);
            }
        }
        return builder.toString();
    }
}
