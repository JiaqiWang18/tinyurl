package com.jwang.hash.repository;

import com.jwang.hash.model.HashEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashRepository extends MongoRepository<HashEntity, String> {
}
