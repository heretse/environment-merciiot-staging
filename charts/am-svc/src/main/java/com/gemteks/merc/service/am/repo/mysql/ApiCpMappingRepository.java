package com.gemteks.merc.service.am.repo.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gemteks.merc.service.am.model.mysql.ApiCpMapping;

@Repository
public interface ApiCpMappingRepository extends CrudRepository<ApiCpMapping, Integer> {
}