package com.gemteks.merc.service.layout.repo.mysql;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.gemteks.merc.service.layout.model.mysql.ApiLoginHistory;

@Repository
public interface ApiLoginHistoryRepository extends CrudRepository<ApiLoginHistory, Integer> {
    @Query(value = "SELECT * FROM api_login_history WHERE  history_logout_time IS NULL AND usertoken = (:token)", nativeQuery = true)
    List<ApiLoginHistory> findLonginToken(@Param("token") String token);
}