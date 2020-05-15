package com.gemteks.merc.service.am.repo.mysql;

import com.gemteks.merc.service.am.model.mysql.ApiFunction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ApiMappingRepository extends CrudRepository<ApiFunction, Integer> {
    // CP
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user SET cpId = -1, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE cpId = :cpId AND userId NOT IN :userId", nativeQuery = true)
    Integer updateUserCpIdByCpIdAndUserId(@Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser,
                                    @Param("cpId") Integer cpId, @Param("userId") List<Integer> userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user SET cpId = :cpId, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE userId IN :userId", nativeQuery = true)
    Integer updateUserCpIdByUserId(@Param("cpId") Integer cpId, @Param("updateTime") Date updateTime,
                                      @Param("updateUser") Integer updateUser, @Param("userId") List<Integer> userId);

    // Role
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user SET roleId = -1, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE roleId = :roleId AND userId NOT IN :userId", nativeQuery = true)
    Integer updateUserRoleIdByRoleIdAndUserId(@Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser,
                                      @Param("roleId") Integer roleId, @Param("userId") List<Integer> userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user SET roleId = :roleId, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE userId IN :userId", nativeQuery = true)
    Integer updateUserRoleIdByUserId(@Param("roleId") Integer roleId, @Param("updateTime") Date updateTime,
                                        @Param("updateUser") Integer updateUser, @Param("userId") List<Integer> userId);

    // GFunc
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_function SET grpId = -1, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE grpId = :grpId AND functionId NOT IN :functionId", nativeQuery = true)
    Integer updateFunctionMappingGrpIdByGrpIdAndFunctionId(@Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser,
                                                           @Param("grpId") Integer grpId, @Param("functionId") List<Integer> functionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_function SET grpId = :grpId, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE functionId IN :functionId", nativeQuery = true)
    Integer updateFunctionMappingGrpIdByFunctionId(@Param("grpId") Integer grpId, @Param("updateTime") Date updateTime,
                                                   @Param("updateUser") Integer updateUser, @Param("functionId") List<Integer> functionId);


    // Delete
    // Egrp
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_user_mapping WHERE locId = :locId AND userId NOT IN :userId", nativeQuery = true)
    Integer deleteUserMappingByLoIdAndUserId(@Param("locId") Integer locId, @Param("userId") List<Integer> userId);

    // Func
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_function_mapping WHERE roleId = :roleId AND functionId NOT IN :functionId", nativeQuery = true)
    Integer deleteFunctionMappingByRoleIdAndFunctionId(@Param("roleId") Integer roleId, @Param("functionId") List<Integer> functionId);

    // GCp
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_cp_mapping WHERE cpId = :cpId AND grpId NOT IN :grpId", nativeQuery = true)
    Integer deleteCpMappingByCpIdAndGrpId(@Param("cpId") Integer cpId, @Param("grpId") List<Integer> grpId);

    // GRole
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_ra_mapping WHERE roleId = :roleId AND grpId NOT IN :grpId", nativeQuery = true)
    Integer deleteRaMappingByRoleIdAndGrpId(@Param("roleId") Integer roleId, @Param("grpId") List<Integer> grpId);


    // Update
    // use loop
    // Egrp
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user_mapping SET locId = :locId, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE userId = :userId", nativeQuery = true)
    Integer updateUserMappingLocIdByUserId(@Param("locId") Integer locId, @Param("updateTime") Date updateTime,
                                          @Param("updateUser") Integer updateUser, @Param("userId") Integer userId);

    // Func
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_function_mapping SET updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE roleId = :roleId AND functionId = :functionId", nativeQuery = true)
    Integer updateFunctionMappingByRoleIdAndFunctionId(@Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser,
                                          @Param("roleId") Integer roleId, @Param("functionId") Integer functionId);

    // GCp
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_cp_mapping SET updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE cpId = :cpId AND grpId = :grpId", nativeQuery = true)
    Integer updateCpMappingByCpIdAndGrpId(@Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser,
                                  @Param("cpId") Integer cpId, @Param("grpId") Integer grpId);

    // GRole
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_ra_mapping SET createFlg = :createFlg, updateFlg = :updateFlg, deleteFlg = :deleteFlg, sortId = :sortId, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE roleId = :roleId AND grpId = :grpId", nativeQuery = true)
    Integer updateRaMappingByRoleIdAndGrpId(@Param("createFlg") Integer createFlg, @Param("updateFlg") Integer updateFlg, @Param("deleteFlg") Integer deleteFlg,
                                    @Param("sortId") Integer sortId, @Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser,
                                    @Param("roleId") Integer roleId, @Param("grpId") Integer grpId);

    // Insert
    // use loop
    // Egrp
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_user_mapping (userId, locId, createTime, createUser) "
            + "VALUES (:userId, :locId, :createTime, :createUser)", nativeQuery = true)
    Integer insertUserMapping(@Param("userId") Integer userId, @Param("locId") Integer locId,
                           @Param("createTime") Date createTime, @Param("createUser") Integer createUser);

    // Func
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_function_mapping (roleId, functionId, createTime, createUser) "
            + "VALUES (:roleId, :functionId, :createTime, :createUser)", nativeQuery = true)
    Integer insertFunctionMapping(@Param("roleId") Integer roleId, @Param("functionId") Integer functionId,
                           @Param("createTime") Date createTime, @Param("createUser") Integer createUser);

    // GCp
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_cp_mapping (cpId, grpId, createTime, createUser) "
            + "VALUES (:cpId, :grpId, :createTime, :createUser)", nativeQuery = true)
    Integer insertCpMapping(@Param("cpId") Integer cpId, @Param("grpId") Integer grpId,
                           @Param("createTime") Date createTime, @Param("createUser") Integer createUser);
    // GRole
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_ra_mapping (roleId, grpId, sortId, createFlg, updateFlg, deleteFlg, createTime, createUser) "
            + "VALUES (:roleId, :grpId, :sortId, :createFlg, :updateFlg, :deleteFlg, :createTime, :createUser)", nativeQuery = true)
    Integer insertRaMapping(@Param("roleId") Integer roleId, @Param("grpId") Integer grpId,
                            @Param("sortId") Integer sortId, @Param("createFlg") Integer createFlg,
                            @Param("updateFlg") Integer updateFlg, @Param("deleteFlg") Integer deleteFlg,
                            @Param("createTime") Date createTime, @Param("createUser") Integer createUser);


    // no data
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user SET cpId = -1, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE cpId = :cpId", nativeQuery = true)
    Integer updateUserCpIdByCpId(@Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser,
                                              @Param("cpId") Integer cpId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user SET roleId = -1, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE roleId = :roleId", nativeQuery = true)
    Integer updateUserRoleIdByRoleId(@Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser,
                                              @Param("roleId") Integer roleId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_user_mapping WHERE locId = :locId", nativeQuery = true)
    Integer deleteUserMappingByLocId(@Param("locId") Integer locId);
}
