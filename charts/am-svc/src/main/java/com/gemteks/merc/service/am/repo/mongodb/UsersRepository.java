package com.gemteks.merc.service.am.repo.mongodb;

import com.gemteks.merc.service.am.model.mongodb.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
}
