package com.gemteks.merc.service.am.repo.mysql;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ApiMappingRollBackRepository {
    @PersistenceContext
    EntityManager entityManager;

    public Integer updateUserCpId(Date updateTime, Integer updateUser, Integer cpId, List<Integer> userId){
        List<Integer> result = null;
        Session session = (Session) entityManager.getDelegate();
        session.beginTransaction();
        result = session.doReturningWork(new ReturningWork<List<Integer>>() {
            @Override
            public List<Integer> execute(Connection conn) throws SQLException {
                List<Integer> result = new ArrayList<Integer>();
                String sql = "UPDATE api_user SET cpId = -1, updateTime = :updateTime, updateUser = :updateUser "
                        + "WHERE cpId = :cpId AND userId NOT IN :userId";
                Query query = entityManager.createNativeQuery(sql);
                query.setParameter("updateTime", updateTime);
                query.setParameter("updateUser", updateUser);
                query.setParameter("cpId", cpId);
                query.setParameter("userId", userId);
                query.executeUpdate();

                sql = "UPDATE api_user SET cpId = :cpId, updateTime = :updateTime, updateUser = :updateUser "
                        + "WHERE userId IN :userId";
                query = entityManager.createNativeQuery(sql);
                query.setParameter("updateTime", updateTime);
                query.setParameter("updateUser", updateUser);
                query.setParameter("cpId", cpId);
                query.setParameter("userId", userId);

                result.add(query.executeUpdate());
                return result;
            }
        });

        session.getTransaction().commit();
        session.close();
        return result.get(0);
    }

    public Integer updateUserRoleIdByRoleId(Date updateTime, Integer updateUser, Integer roleId, List<Integer> userId){
        List<Integer> result = null;
        Session session = (Session) entityManager.getDelegate();
        session.beginTransaction();
        result = session.doReturningWork(new ReturningWork<List<Integer>>() {
            @Override
            public List<Integer> execute(Connection conn) throws SQLException {
                List<Integer> result = new ArrayList<Integer>();
                String sql = "UPDATE api_user SET roleId = -1, updateTime = :updateTime, updateUser = :updateUser "
                        + "WHERE roleId = :roleId AND userId NOT IN :userId";
                Query query = entityManager.createNativeQuery(sql);
                query.setParameter("updateTime", updateTime);
                query.setParameter("updateUser", updateUser);
                query.setParameter("roleId", roleId);
                query.setParameter("userId", userId);
                query.executeUpdate();

                sql = "UPDATE api_user SET roleId = :roleId, updateTime = :updateTime, updateUser = :updateUser "
                        + "WHERE userId IN :userId";
                query = entityManager.createNativeQuery(sql);
                query.setParameter("updateTime", updateTime);
                query.setParameter("updateUser", updateUser);
                query.setParameter("roleId", roleId);
                query.setParameter("userId", userId);

                result.add(query.executeUpdate());
                return result;
            }
        });

        session.getTransaction().commit();
        session.close();
        return result.get(0);
    }

    public Integer updateFunctionMappingGrpId(Date updateTime, Integer updateUser, Integer grpId, List<Integer> functionId){
        List<Integer> result = null;
        Session session = (Session) entityManager.getDelegate();
        session.beginTransaction();
        result = session.doReturningWork(new ReturningWork<List<Integer>>() {
            @Override
            public List<Integer> execute(Connection conn) throws SQLException {
                List<Integer> result = new ArrayList<Integer>();
                String sql = "UPDATE api_function SET grpId = -1, updateTime = :updateTime, updateUser = :updateUser "
                        + "WHERE grpId = :grpId AND functionId NOT IN :functionId";
                Query query = entityManager.createNativeQuery(sql);
                query.setParameter("updateTime", updateTime);
                query.setParameter("updateUser", updateUser);
                query.setParameter("grpId", grpId);
                query.setParameter("functionId", functionId);
                query.executeUpdate();

                sql = "UPDATE api_function SET grpId = :grpId, updateTime = :updateTime, updateUser = :updateUser "
                        + "WHERE functionId IN :functionId";
                query = entityManager.createNativeQuery(sql);
                query.setParameter("updateTime", updateTime);
                query.setParameter("updateUser", updateUser);
                query.setParameter("grpId", grpId);
                query.setParameter("functionId", functionId);

                result.add(query.executeUpdate());
                return result;
            }
        });

        session.getTransaction().commit();
        session.close();
        return result.get(0);
    }
}
