package com.gemteks.merc.service.am.repo.mysql;

import com.gemteks.merc.service.am.model.mysql.projection.LoginListItem;
import com.gemteks.merc.service.am.model.mysql.projection.RaMappingListItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gemteks.merc.service.am.model.mysql.ApiUserSocialMapping;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ApiUserSocialMappingRepository extends CrudRepository<ApiUserSocialMapping, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_user_social_mapping (userId, socialType, socialId, createTime, createUser) "
            + "SELECT * FROM (SELECT :existingUserId AS userId, :socialProvider, :socialUid, :createTime, :existingUserId AS createUser) AS tmp "
            + "WHERE NOT EXISTS (SELECT mappingId "
            + "FROM api_user_social_mapping "
            + "WHERE socialType = :socialProvider AND socialId = :socialUid)", nativeQuery = true)
    Integer insertUserSocialMapping(@Param("existingUserId") Integer existingUserId, @Param("socialProvider") String socialProvider,
                                    @Param("socialUid") String socialUid, @Param("createTime") Date createTime);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM api_user_social_mapping WHERE userId = :existingUserId AND socialType = :socialProvider AND socialId = :socialUid", nativeQuery = true)
    Integer deleteUserSocialMapping(@Param("existingUserId") Integer existingUserId, @Param("socialProvider") String socialProvider, @Param("socialUid") String socialUid);

    @Query(value = "SELECT usr.*, r.roleName, r.dataset FROM ((SELECT * FROM api_user WHERE userBlock = 0 "
            + "AND cpId = (SELECT cpId FROM api_cp WHERE cpName = :cpName) "
            + "AND userId = (SELECT userId FROM api_user_social_mapping "
            + "WHERE  socialType = :socialType AND socialId = :socialId)) AS usr "
            + "LEFT JOIN api_role AS r ON usr.roleId = r.roleId)", nativeQuery = true)
    List<LoginListItem> getUserSocialMapping(@Param("cpName") String cpName, @Param("socialType") String socialType,
                                             @Param("socialId") String socialId);


    @Query(value = "SELECT g.grpId, g.grpName, g.createTime, m.createFlg, m.updateFlg, m.deleteFlg, m.sortId "
            + "FROM api_ra_mapping m LEFT JOIN api_grp g ON m.grpId = g.grpId "
            + "WHERE m.roleId = :roleId ORDER BY m.sortId", nativeQuery = true)
    List<RaMappingListItem> getUserGrp(@Param("roleId") Integer roleId);
}