package com.gemteks.merc.service.am.repo.mongodb;

import com.gemteks.merc.service.am.model.mongodb.ShowField;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowFieldRepository extends MongoRepository<ShowField, String> {
    
}