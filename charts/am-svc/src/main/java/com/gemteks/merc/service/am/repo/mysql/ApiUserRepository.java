package com.gemteks.merc.service.am.repo.mysql;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gemteks.merc.service.am.model.mysql.ApiUser;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface ApiUserRepository extends CrudRepository<ApiUser, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user SET cpId = :cpId, roleId = :roleId, userBlock = :userBlock, updateTime = :updateTime, updateUser = :updateUser "
            + "WHERE userId = :userId", nativeQuery = true)
    Integer updateUserStatus(@Param("cpId") Integer cpId, @Param("roleId") Integer roleId, @Param("userBlock") Integer userBlock,
                             @Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE api_user SET cpId = :cpId, roleId = :roleId, userBlock = :userBlock, updateTime = :updateTime, updateUser = :updateUser, userPwd = :pwd "
            + "WHERE userId = :userId", nativeQuery = true)
    Integer updateUserStatusWithPwd(@Param("cpId") Integer cpId, @Param("roleId") Integer roleId, @Param("userBlock") Integer userBlock,
                                    @Param("updateTime") Date updateTime, @Param("updateUser") Integer updateUser, @Param("pwd") String pwd,
                                    @Param("userId") Integer userId);

    @Query(value = "SELECT * FROM api_user WHERE cpId = :cpId AND (userName = :userName OR email = :email)", nativeQuery = true)
    List<ApiUser> checkUserExist(@Param("cpId") Integer cpId, @Param("userName") String userName, @Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_user (cpId, roleId, userName, nickName, gender, userPwd, deviceType, pic, email, userBlock, userType, createTime, createUser) "
            + "VALUES (:catId, :roleId, :userName, :nickName, :gender, :userPwd, :deviceType, :pic, :email, :userBlock, :userType, :createTime, :createUser)", nativeQuery = true)
    Integer insertUserByAdmin(@Param("catId") Integer catId, @Param("roleId") Integer roleId, @Param("userName") String userName, @Param("nickName") String nickName,
                              @Param("gender") String gender, @Param("userPwd") String userPwd, @Param("deviceType") Integer deviceType, @Param("pic") String pic,
                              @Param("email") String email, @Param("userBlock") Integer userBlock, @Param("userType") Integer userType, @Param("createTime") Date createTime,
                              @Param("createUser") Integer createUser);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    BigInteger getLastId();

    @Modifying
    @Transactional
    @Query(value = "delete from api_user where userId = :userId", nativeQuery = true)
    Integer deleteUserByUserId(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "delete from api_user where cpId = :cpId and userId = :userId", nativeQuery = true)
    Integer deleteUserByUserIdAndCpId(@Param("cpId") Integer cpId, @Param("userId") Integer userId);
}