package com.jwang.hash.service;

import com.jwang.hash.model.UsedHashEntity;
import com.jwang.hash.repository.HashRepository;
import com.jwang.hash.repository.UsedHashRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetrieveService {

    @Autowired
    HashRepository hashRepository;

    @Autowired
    UsedHashRepository usedHashRepository;

    Logger logger = LoggerFactory.getLogger(GeneratorService.class);


    /**
     * Synchronized method that retrieves a hash from unused db, removes it, and adds it to used hash
     * @return hash
     */
    @Transactional
    public synchronized String retrieveOne(){
        logger.info("start to retrieve a hash");
        String retrievedHash = hashRepository.findAll().get(0).getHash();
        logger.info("retrieved hash: "+retrievedHash);
        usedHashRepository.save(new UsedHashEntity(retrievedHash));
        hashRepository.deleteById(retrievedHash);
        logger.info("retrieved hash removed from unused");
        return retrievedHash;
    }
}
