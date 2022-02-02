package com.jwang.shortener.repository;

import com.jwang.shortener.model.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
    List<UrlEntity> findUrlEntitiesByUsername(String username);
}
