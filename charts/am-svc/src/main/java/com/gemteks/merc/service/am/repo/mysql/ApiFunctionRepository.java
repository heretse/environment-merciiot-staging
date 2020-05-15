package com.gemteks.merc.service.am.repo.mysql;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gemteks.merc.service.am.model.mysql.ApiFunction;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ApiFunctionRepository extends CrudRepository<ApiFunction, Integer> {
    @Query(value = "SELECT * FROM api_function WHERE functionName = :functionName", nativeQuery = true)
    List<ApiFunction> getAllFunctionByFunctionName(@Param("functionName") String functionName);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_function (functionName, functionUrl, parentId, sortId, hiddenFlg, delFlg, grpId, createTime, createUser) "
            + "VALUES (:functionName, :functionUrl, :parentId, :sortId, :hiddenFlg, :delFlg, :grpId, :createTime, :createUser)", nativeQuery = true)
    Integer insertFunction(@Param("functionName") String functionName, @Param("functionUrl") String functionUrl, @Param("parentId") Integer parentId,
                       @Param("sortId") Integer sortId, @Param("hiddenFlg") String hiddenFlg, @Param("delFlg") Integer delFlg,
                       @Param("grpId") Integer grpId, @Param("createTime") Date createTime, @Param("createUser") Integer createUser);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_function SET functionName = :functionName, functionUrl = :functionUrl, parentId = :parentId, "
            + "sortId = :sortId, hiddenFlg = :hiddenFlg, grpId = :grpId, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE functionId = :functionId", nativeQuery = true)
    Integer updateFunctionByFunctionId(@Param("functionName") String functionName, @Param("functionUrl") String functionUrl, @Param("parentId") Integer parentId,
                                       @Param("sortId") Integer sortId, @Param("hiddenFlg") String hiddenFlg, @Param("grpId") Integer grpId,
                                       @Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser, @Param("functionId") Integer functionId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_function WHERE delFlg = 1 AND grpId IN :grpId AND functionId = :functionId", nativeQuery = true)
    Integer deleteFunctionByFunctionId(@Param("grpId") List<Integer> grpId, @Param("functionId") Integer functionId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_function_mapping WHERE functionId = :functionId", nativeQuery = true)
    Integer deleteFunctionMappingByFunctionId(@Param("functionId") Integer functionId);

}