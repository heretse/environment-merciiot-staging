package com.gemteks.merc.service.am.repo.mysql;

import com.gemteks.merc.service.am.model.mysql.projection.GrpListItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gemteks.merc.service.am.model.mysql.ApiGrp;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ApiGrpRepository extends CrudRepository<ApiGrp, Integer> {
    @Query(value = "SELECT grpId, grpName, createTime FROM api_grp "
            + "WHERE 1 = 1 AND grpName LIKE %:searchText% ORDER BY createTime DESC", nativeQuery = true)
    List<GrpListItem> getAllGrp(@Param("searchText") String searchText);

    @Query(value = "SELECT g.grpId, g.grpName, g.createTime FROM api_cp_mapping m LEFT JOIN api_grp g ON m.grpId = g.grpId "
            + "WHERE m.cpId = :cpId AND grpName LIKE %:searchText% ORDER BY createTime DESC", nativeQuery = true)
    List<GrpListItem> getGrpByCpId(@Param("cpId") Integer cpId, @Param("searchText") String searchText);

    @Query(value = "SELECT * FROM api_grp WHERE grpName = :grpName", nativeQuery = true)
    List<ApiGrp> getAllGrpByGrpName(@Param("grpName") String grpName);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_grp (grpName, createTime, createUser) "
            + "VALUES (:grpName, :createTime, :userId)", nativeQuery = true)
    Integer insertGrp(@Param("grpName") String grpName, @Param("createTime") Date createTime, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_grp SET grpName = :grpName, updateTime = :updateTime, updateUser = :updateUser WHERE grpId = :grpId", nativeQuery = true)
    Integer updateGrpByGrpId(@Param("grpName") String grpName, @Param("updateTime") Date updateTime,
                             @Param("updateUser") Integer updateUser, @Param("grpId") Integer grpId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_grp WHERE grpId = :grpId", nativeQuery = true)
    Integer deleteGrpByGrpId(@Param("grpId") Integer grpId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_ra_mapping WHERE grpId = :grpId", nativeQuery = true)
    void deleteRaMappingByGrpId(@Param("grpId") Integer grpId);
}