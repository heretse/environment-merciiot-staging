package com.gemteks.merc.service.am.repo.mysql;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class ApiFunctionMgmRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List getAllFunctionInfo(String searchText){
        String sql = "SELECT f.functionId, f.functionName, f.functionUrl, f.parentId, f.sortId, f.hiddenFlg, f.delFlg, f.createTime, f.grpId "
                + "FROM api_function f "
                + "WHERE 1 =1 ";

        Query query;
        if (!searchText.equals("") && searchText.length() > 0){
            if (searchText.contains(":")){
                String[] condArrs = searchText.split("&");
                String param01 = null;
                String param02 = null;
                // roleId:[= or !=]:1&grpId:[= or !=]:1
                for (String condArr : condArrs) {
                    String[] arr = condArr.split(":");
                    if (arr.length == 3) {
                        if (arr[0].equals("roleId")) {
                            param01 = arr[2];
                            if (arr[1].equals("=")) {
                                sql = sql + "AND f.functionId IN (SELECT functionId FROM api_function_mapping WHERE " + arr[0] + " = " + ":param01) ";
                            }
                            if (arr[1].equals("!=")) {
                                sql = sql + "AND f.functionId NOT IN (SELECT functionId FROM api_function_mapping WHERE " + arr[0] + " = " + ":param01) ";
                            }
                        }
                        if (arr[0].equals("grpId")) {
                            param02 = arr[2];
                            if (arr[1].equals("=")) {
                                sql = sql + "AND f.grpId IN " + "(:param02) ";
                            }
                            if (arr[1].equals("!=")) {
                                sql = sql + "AND f.grpId NOT IN " + "(:param02) ";
                            }
                        }
                    }
                }
                sql = sql + " ORDER BY sortId";
                query = entityManager.createNativeQuery(sql);
                if (param01 != null) {
                    query.setParameter("param01", param01);
                }
                if (param02 != null) {
                    query.setParameter("param02", param02);
                }
            }
            else {
                sql = sql + "AND f.functionName LIKE " + ":searchText";
                sql = sql + " ORDER BY sortId";
                query = entityManager.createNativeQuery(sql);
                query.setParameter("searchText", "%" + searchText + "%");
            }
        }
        else {
            sql = sql + " ORDER BY sortId";
            query = entityManager.createNativeQuery(sql);
        }

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.getResultList();
    }
}
