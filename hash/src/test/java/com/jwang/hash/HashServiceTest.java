package com.jwang.hash;

import com.jwang.hash.repository.HashRepository;
import com.jwang.hash.repository.UsedHashRepository;
import com.jwang.hash.service.RetrieveService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HashServiceTest {

    @Autowired
    RetrieveService retrieveService;

    @Autowired
    UsedHashRepository usedHashRepository;

    @Autowired
    HashRepository hashRepository;

    //@Test
    public void testRetrieve (){
        String hash = retrieveService.retrieveOne();
        assertFalse(hashRepository.existsById(hash));
        assertTrue(usedHashRepository.existsById(hash));
        System.out.println(hash);
    }
}
