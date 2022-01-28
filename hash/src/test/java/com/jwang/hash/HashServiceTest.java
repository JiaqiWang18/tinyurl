package com.jwang.hash;

import com.jwang.hash.service.GeneratorService;
import com.jwang.hash.service.RetrieveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashServiceTest {

    @Autowired
    GeneratorService generatorService;

    @Autowired
    RetrieveService retrieveService;

    public void ingestData(){
        generatorService.ingestHashes(10);
    }

    public void testRetrieve (){
        String hash = retrieveService.retrieveOne();
        System.out.println(hash);
    }
}
