package com.gemteks.merc.service.am.repo.mysql;

import com.gemteks.merc.service.am.model.mysql.ApiSystemProperties;
import com.gemteks.merc.service.am.model.mysql.projection.LoginListItem;
import com.gemteks.merc.service.am.model.mysql.projection.RaMappingListItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ApiLoginRepository extends CrudRepository<ApiSystemProperties, Integer> {
    @Query(value = "SELECT usr.*, r.roleName, r.dataset "
            + "FROM ((select * from api_user "
            + "WHERE cpId =(SELECT cpId FROM api_cp WHERE cpName = :cpName) "
            + "AND (userName = :acc or email = :acc)) "
            + "AS usr LEFT JOIN api_role AS r ON usr.roleId = r.roleId)", nativeQuery = true)
    List<LoginListItem> checkUserExist(@Param("cpName") String cpName, @Param("acc") String acc);

    @Query(value = "SELECT g.grpId, g.grpName, g.createTime, m.createFlg, m.updateFlg, m.deleteFlg, m.sortId "
            + "FROM api_ra_mapping m LEFT JOIN api_grp g ON m.grpId = g.grpId "
            + "WHERE m.roleId = :roleId ORDER BY m.sortId", nativeQuery = true)
    List<RaMappingListItem> getUserGrp(@Param("roleId") Integer roleId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_login_history (userId, userToken, history_login_time, history_ip, history_type, createTime) "
            + "VALUES (:userId, :userToken, :history_login_time, :history_ip, :history_type, :createTime)", nativeQuery = true)
    Integer insertLoginHistory(@Param("userId") Integer userId, @Param("userToken") String userToken, @Param("history_login_time") Date history_login_time,
                       @Param("history_ip") String history_ip, @Param("history_type") Integer history_type, @Param("createTime") Date createTime);

    @Query(value = "SELECT * FROM api_system_properties "
            + "WHERE p_name = \"TOKEN_EXPIRE\"", nativeQuery = true)
    ApiSystemProperties getTokenExpireProperty();
}
