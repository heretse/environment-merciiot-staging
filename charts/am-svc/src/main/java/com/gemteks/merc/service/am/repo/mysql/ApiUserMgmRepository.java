package com.gemteks.merc.service.am.repo.mysql;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Component
public class ApiUserMgmRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List getAllUserInfo(String searchText){
        String sql = "SELECT u.userId, u.cpId, u.roleId, u.userName, u.pic, u.email, u.userBlock, u.userType, u.createTime, c.cpName, r.roleName, "
                + "CASE WHEN u.userBlock = 0 THEN \"unblock\"  WHEN u.userBlock = 1 THEN \"block\" ELSE \"unknown\" END AS blockDesc "
                + "FROM api_user u LEFT JOIN api_cp c ON u.cpId = c.cpId LEFT JOIN api_role r ON u.roleId = r.roleId "
                + "WHERE 1 =1 ";

        Query query;
        if (!searchText.equals("") && searchText.length() > 0){
            if (searchText.contains(":")){
                String[] arr = searchText.split(":");
                if (arr.length == 3){
                    // e.g. u.cpId = 1
                    sql = sql + "AND u." + arr[0] + " " + arr[1] + " " + ":param01";
                    query = entityManager.createNativeQuery(sql);
                    query.setParameter("param01", arr[2]);
                }
                else if (arr.length == 5){
                    // e.g. u.createTime BETWEEN XXX AND XXX
                    sql = sql + "AND u." + arr[0] + " " + arr[1] + " " + ":param01" + " " + arr[3] + " " + ":param02";
                    query = entityManager.createNativeQuery(sql);
                    query.setParameter("param01", arr[2]);
                    query.setParameter("param02", arr[4]);
                }
                else {
                    query = entityManager.createNativeQuery(sql);
                }
            }
            else {
                sql = sql + "AND (u.userName LIKE " + ":searchText" + " OR u.email LIKE " + ":searchText" + " OR c.cpName LIKE " + ":searchText" + ")";
                query = entityManager.createNativeQuery(sql);
                query.setParameter("searchText", "%" + searchText + "%");
            }
        }
        else {
            query = entityManager.createNativeQuery(sql);
        }

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.getResultList();
    }

    public List getAllUserInfoByCpId(String searchText, String cpId){
        String sql = "select u.userId, u.cpId, u.roleId, u.userName, u.pic, u.email, u.userBlock, u.userType, u.createTime, c.cpName, r.roleName, "
                + "case when u.userBlock = 0 then \"unblock\"  when u.userBlock = 1 then \"block\" else \"unknown\" end as blockDesc "
                + "from api_user u left join api_cp c on u.cpId = c.cpId left join api_role r on u.roleId = r.roleId "
                + "where u.cpId = :cpId ";

        Query query;
        if (!searchText.equals("") && searchText.length() > 0){
            if (searchText.contains(":")){
                String[] arr = searchText.split(":");
                if (arr.length == 3){
                    // e.g. u.cpId = 1
                    sql = sql + "AND u." + arr[0] + " " + arr[1] + " " + "param01";
                    query = entityManager.createNativeQuery(sql);
                    query.setParameter("param01", arr[2]);
                }
                else if (arr.length == 5){
                    // e.g. u.createTime BETWEEN XXX AND XXX
                    sql = sql + "AND u." + arr[0] + " " + arr[1] + " " + ":param01" + " " + arr[3] + " " + ":param02";
                    query = entityManager.createNativeQuery(sql);
                    query.setParameter("param01", arr[2]);
                    query.setParameter("param02", arr[4]);
                }
                else {
                    query = entityManager.createNativeQuery(sql);
                }
            }
            else {
                sql = sql + "AND (u.userName LIKE " + ":searchText" + " OR u.email LIKE " + ":searchText" + " OR c.cpName LIKE " + ":searchText" + ")";
                query = entityManager.createNativeQuery(sql);
                query.setParameter("searchText", "%" + searchText + "%");
            }
        }
        else {
            query = entityManager.createNativeQuery(sql);
        }

        query.setParameter("cpId", cpId);

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.getResultList();
    }
}
