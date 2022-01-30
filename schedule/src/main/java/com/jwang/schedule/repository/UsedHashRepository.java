package com.jwang.schedule.repository;

import com.jwang.schedule.model.UsedHashEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedHashRepository extends MongoRepository<UsedHashEntity, String> {
}
