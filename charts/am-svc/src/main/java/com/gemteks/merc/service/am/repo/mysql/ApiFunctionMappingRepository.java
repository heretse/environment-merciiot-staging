package com.gemteks.merc.service.am.repo.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gemteks.merc.service.am.model.mysql.ApiFunctionMapping;

@Repository
public interface ApiFunctionMappingRepository extends CrudRepository<ApiFunctionMapping, Integer> {
}