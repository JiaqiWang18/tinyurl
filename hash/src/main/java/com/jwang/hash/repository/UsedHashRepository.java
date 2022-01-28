package com.jwang.hash.repository;

import com.jwang.hash.model.UsedHashEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedHashRepository extends MongoRepository<UsedHashEntity, String> {
}
