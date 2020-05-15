package com.gemteks.merc.service.layout.repo.mysql;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

import javax.transaction.Transactional;

import com.gemteks.merc.service.layout.model.mysql.ApiUser;

@Repository
public interface ApiUserRepository extends CrudRepository<ApiUser, Integer> {

  @Modifying
  @Transactional
  @Query(value = "UPDATE api_user SET pic = :picId, updateTime = :time, updateUser = :userId WHERE userId = :userId", nativeQuery = true)
  Integer updateUser(@Param("picId") String picId, @Param("userId") Integer userId, @Param("time") Date time);

}