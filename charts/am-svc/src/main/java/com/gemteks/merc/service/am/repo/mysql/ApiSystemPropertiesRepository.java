package com.gemteks.merc.service.am.repo.mysql;

import com.gemteks.merc.service.am.model.mysql.projection.SysPropertyListItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import com.gemteks.merc.service.am.model.mysql.ApiSystemProperties;

import javax.transaction.Transactional;

@Repository
public interface ApiSystemPropertiesRepository extends CrudRepository<ApiSystemProperties, Integer> {
    List<ApiSystemProperties> findBypName(String pName);

    @Query(value = "SELECT * FROM api_system_properties WHERE p_name = :p_name", nativeQuery = true)
    List<ApiSystemProperties> findPropertiesByName(@Param("p_name") String p_name);

    @Query(value = "SELECT p_name, p_value, p_desc, p_type, createTime FROM api_system_properties "
            + "WHERE (p_type = \"ALL\" OR p_type IN :p_type) "
            + "and p_name like %:searchText%", nativeQuery = true)
    List<SysPropertyListItem> getAllSysProperty(@Param("p_type") List<String> p_type, @Param("searchText") String searchText);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_system_properties (p_name, p_value, p_desc, p_type, createTime, createUser) "
            + "VALUES (:p_name, :p_value, :p_desc, :p_type, :createTime, :userId)", nativeQuery = true)
    Integer insertSysProperty(@Param("p_name") String p_name, @Param("p_value") String p_value, @Param("p_desc") String p_desc,
                       @Param("p_type") String p_type, @Param("createTime") Date createTime, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_system_properties SET p_value = :p_value, p_desc = :p_desc, p_type = :p_type, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE p_name = :p_name", nativeQuery = true)
    Integer updateSysPropertyByPName(@Param("p_value") String p_value, @Param("p_desc") String p_desc, @Param("p_type") String p_type,
                               @Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser, @Param("p_name") String p_name);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_system_properties WHERE p_name = :p_name", nativeQuery = true)
    Integer deleteSysPropertyByPName(@Param("p_name") String p_name);
}