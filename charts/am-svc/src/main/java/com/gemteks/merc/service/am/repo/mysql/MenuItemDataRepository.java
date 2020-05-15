package com.gemteks.merc.service.am.repo.mysql;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gemteks.merc.service.am.model.mysql.ApiFunction;
import com.gemteks.merc.service.am.model.mysql.projection.MenuItem;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemDataRepository extends CrudRepository<ApiFunction, Integer> {
    @Query(value = "select functionId, functionName, functionUrl, hiddenFlg, parentId from api_function where functionId in (select functionId from api_function_mapping where roleId = (:roleId)) and grpId = (:grpId) and parentId in (0, -1, -2) order by sortId", nativeQuery = true)
    List<MenuItem> findAllMenuItemBy(@Param("roleId") Integer roleId, @Param("grpId") Integer grpId);

    @Query(value = "select functionId, functionName, functionUrl, hiddenFlg, parentId from api_function where parentId = (:funcId) order by sortId", nativeQuery = true)
    List<MenuItem> findAllMenuItemByParentId(@Param("funcId") Integer funcId);
}

