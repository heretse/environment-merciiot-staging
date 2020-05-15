package com.gemteks.merc.service.am.repo.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.gemteks.merc.service.am.model.mysql.ApiRaMapping;

@Repository
public interface ApiRaMappingRepository extends CrudRepository<ApiRaMapping, Integer> {
  
}