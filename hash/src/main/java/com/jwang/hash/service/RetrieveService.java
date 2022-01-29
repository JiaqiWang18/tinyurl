package com.jwang.hash.service;

import com.jwang.hash.model.UsedHashEntity;
import com.jwang.hash.repository.HashRepository;
import com.jwang.hash.repository.UsedHashRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RetrieveService {

    @Autowired
    HashRepository hashRepository;

    @Autowired
    UsedHashRepository usedHashRepository;


    /**
     * Synchronized method that retrieves a hash from unused db, removes it, and adds it to used hash
     * @return hash
     */
    @Transactional
    public synchronized String retrieveOne(){
        log.info("start to retrieve a hash");
        String retrievedHash = hashRepository.findAll().get(0).getHash();
        log.info("retrieved hash: "+retrievedHash);
        usedHashRepository.save(new UsedHashEntity(retrievedHash));
        hashRepository.deleteById(retrievedHash);
        log.info("retrieved hash removed from unused");
        return retrievedHash;
    }

    @Transactional
    public synchronized void markHashAsUsed(String hash){
        log.info("start markHashAsUsed ");
        if(hashRepository.existsById(hash)){
            hashRepository.deleteById(hash);
        }
        usedHashRepository.save(new UsedHashEntity(hash));
    }
}
