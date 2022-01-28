package com.jwang.shortener.repository;

import com.jwang.shortener.model.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
}
