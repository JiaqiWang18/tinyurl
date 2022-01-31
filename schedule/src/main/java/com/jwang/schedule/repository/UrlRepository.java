package com.jwang.schedule.repository;

import com.jwang.schedule.model.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
}
