package com.gemteks.merc.service.am.repo.mysql;

import com.gemteks.merc.service.am.model.mysql.ApiLoginHistory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface ApiLogoutRepository extends CrudRepository<ApiLoginHistory, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_login_history SET history_logout_time = :history_logout_time "
            + "WHERE history_logout_time IS NULL AND userToken = :userToken AND userId = :userId", nativeQuery = true)
    Integer updateLogoutInfo(@Param("history_logout_time") Date history_logout_time,
                               @Param("userToken") String userToken, @Param("userId") Integer userId);
}
