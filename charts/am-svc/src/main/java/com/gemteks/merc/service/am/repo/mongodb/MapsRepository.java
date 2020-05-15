package com.gemteks.merc.service.am.repo.mongodb;

import com.gemteks.merc.service.am.model.mongodb.Maps;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapsRepository extends MongoRepository<Maps, String> {
    
}