package com.jwang.schedule.repository;

import com.jwang.schedule.model.HashEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashRepository extends MongoRepository<HashEntity, String> {
}
