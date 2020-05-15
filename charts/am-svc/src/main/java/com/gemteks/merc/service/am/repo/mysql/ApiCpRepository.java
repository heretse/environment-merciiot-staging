package com.gemteks.merc.service.am.repo.mysql;

import com.gemteks.merc.service.am.model.mysql.projection.CpGrpListItem;
import com.gemteks.merc.service.am.model.mysql.projection.CpListItem;
import com.gemteks.merc.service.am.model.mysql.projection.GrpListItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gemteks.merc.service.am.model.mysql.ApiCp;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@Repository
public interface ApiCpRepository extends CrudRepository<ApiCp, Integer> {
    @Query(value = "SELECT cpId, cpName, createTime FROM api_cp WHERE 1 = 1 AND cpName LIKE %:searchText%", nativeQuery = true)
    List<CpListItem> getAllCp(@Param("searchText") String searchText);

    @Query(value = "SELECT cpId, cpName, createTime FROM api_cp WHERE cpId = :cpId", nativeQuery = true)
    List<CpListItem> getAllCpByCpId(@Param("cpId") Integer cpId);

    @Query(value = "SELECT g.grpId, g.grpName, g.createTime FROM api_cp_mapping m LEFT JOIN api_grp g ON m.grpId = g.grpId WHERE m.cpId = :cpId", nativeQuery = true)
    List<GrpListItem> getGrpByCpId(@Param("cpId") Integer cpId);

    @Query(value = "SELECT * FROM api_cp WHERE cpName = :cpName", nativeQuery = true)
    List<ApiCp> getAllCpByCpName(@Param("cpName") String cpName);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_cp (cpName, createTime, createUser) "
            + "VALUES (:cpName, :createTime, :userId)", nativeQuery = true)
    Integer insertCp(@Param("cpName") String cpName, @Param("createTime") Date createTime, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_cp SET cpName = :cpName, updateTime = :updateTime, updateUser = :updateUser WHERE cpId = :cpId", nativeQuery = true)
    Integer updateCpByCpId(@Param("cpName") String cpName, @Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser, @Param("cpId") Integer cpId);

    @Query(value = "SELECT COUNT(1) AS Cnt FROM api_user WHERE cpId = :cpId", nativeQuery = true)
    Integer countCpByCpId(@Param("cpId") Integer cpId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_cp WHERE cpId = :cpId", nativeQuery = true)
    Integer deleteCpByCpId(@Param("cpId") Integer cpId);

}