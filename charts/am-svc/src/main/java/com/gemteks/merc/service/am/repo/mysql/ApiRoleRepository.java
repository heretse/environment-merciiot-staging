package com.gemteks.merc.service.am.repo.mysql;

import com.gemteks.merc.service.am.model.mysql.projection.RaMappingListItem;
import com.gemteks.merc.service.am.model.mysql.projection.RoleListItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gemteks.merc.service.am.model.mysql.ApiRole;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ApiRoleRepository extends CrudRepository<ApiRole, Integer> {
    @Query(value = "SELECT roleId, roleName, dataset, editFlg, delFlg, createTime FROM api_role "
            + "WHERE (roleId IN (SELECT roleId FROM api_ra_mapping WHERE grpId IN (SELECT grpId FROM api_cp_mapping WHERE cpId = :cpId)) "
            + "OR createUser = :createUser OR updateUser = :updateUser) "
            + "and roleName like %:searchText%", nativeQuery = true)
    List<RoleListItem> getAllRole(@Param("cpId") Integer cpId, @Param("createUser") Integer createUser,
                                  @Param("updateUser") Integer updateUser, @Param("searchText") String searchText);

    @Query(value = "SELECT g.grpId, g.grpName, g.createTime, m.createFlg, m.updateFlg, m.deleteFlg, m.sortId "
            + "FROM api_ra_mapping m LEFT JOIN api_grp g ON m.grpId = g.grpId "
            + "WHERE m.roleId = :roleId ORDER BY m.sortId", nativeQuery = true)
    List<RaMappingListItem> getRaMappingByRoleId(@Param("roleId") Integer roleId);

    @Query(value = "SELECT * FROM api_role WHERE roleName = :roleName", nativeQuery = true)
    List<ApiRole> getAllRoleByRoleName(@Param("roleName") String roleName);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_role (roleName, dataset, editFlg, delFlg, createTime, createUser) "
            + "VALUES (:roleName, :dataset, :editFlg, :delFlg, :createTime, :userId)", nativeQuery = true)
    Integer insertRole(@Param("roleName") String roleName, @Param("dataset") Integer dataset, @Param("editFlg") Integer editFlg,
                       @Param("delFlg") Integer delFlg, @Param("createTime") Date createTime, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_role SET roleName = :roleName, dataset = :dataset, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE editFlg = 1 AND roleId = :roleId", nativeQuery = true)
    Integer updateRoleByRoleId(@Param("roleName") String roleName, @Param("dataset") Integer dataset, @Param("updateTime") Date updateTime,
                               @Param("updateUser") Integer updateUser, @Param("roleId") Integer roleId);

    @Query(value = "SELECT COUNT(1) AS Cnt FROM api_user WHERE roleId = :roleId", nativeQuery = true)
    Integer countRoleByRoleId(@Param("roleId") Integer roleId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_role WHERE delFlg = 1 AND roleId = :roleId", nativeQuery = true)
    Integer deleteRoleByRoleId(@Param("roleId") Integer roleId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_function_mapping WHERE roleId = :roleId", nativeQuery = true)
    void deleteFunctionMappingByRoleId(@Param("roleId") Integer roleId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_ra_mapping WHERE roleId = :roleId", nativeQuery = true)
    void deleteRaMappingByRoleId(@Param("roleId") Integer roleId);
}