package com.jwang.schedule.task;

import com.jwang.schedule.model.HashEntity;
import com.jwang.schedule.repository.HashRepository;
import com.jwang.schedule.repository.UrlRepository;
import com.jwang.schedule.repository.UsedHashRepository;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Component
public class ExpireHash {
    private final HashRepository hashRepository;

    private final UsedHashRepository usedHashRepository;

    private final UrlRepository urlRepository;

    private final MongoTemplate mongoTemplate;

    public ExpireHash(HashRepository hashRepository, UsedHashRepository usedHashRepository, UrlRepository urlRepository, MongoTemplate mongoTemplate) {
        this.hashRepository = hashRepository;
        this.usedHashRepository = usedHashRepository;
        this.urlRepository = urlRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Scheduled(cron="0 0 0 * * ?", zone = "America/New_York")
    @Transactional
    public void expireHash(){
        log.info("expire hash start");
        MongoCollection<Document> collection = mongoTemplate.getCollection("urlmap");
        FindIterable<Document> docs = collection.find();
        for (Document doc:docs) {
            String hash = doc.getString("_id");
            if(!urlRepository.existsById(hash)){
                continue;
            }
            LocalDate expireDate = urlRepository.findById(hash).get().getExpireDate();
            if(expireDate.isBefore(LocalDate.now())){
                log.info(hash+ " is expired and will be recycled");
                urlRepository.deleteById(hash);
                usedHashRepository.deleteById(hash);
                hashRepository.save(new HashEntity(hash));
            }
        }
    }
}
