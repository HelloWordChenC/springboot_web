package com.base.dao.Impl;

import com.base.dao.BaseDaoI;
import com.base.dao.until.Grid;
import com.base.dao.until.PageFilter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BaseDaoImpl<T> implements BaseDaoI<T> {

  /*  @Autowired
    private SessionFactory sessionFactory;
    */
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private Session sessionFactory;

    public Session getSession() {
        if(sessionFactory==null){
            sessionFactory  =entityManagerFactory.unwrap(SessionFactory.class).openSession();
            return sessionFactory;
        }
        return  sessionFactory;
    }


    /**
     * 获得当前事物的session
     *
     * @return org.hibernate.Session
     */
    public Session getCurrentSession() {
        return getSession();
    }

    @Override
    public Serializable save(T o) {
        if (o != null) {
            return this.getCurrentSession().save(o);
        }
        return null;
    }

    @Override
    public T get(Class<T> c, Serializable id) {
        return (T) this.getCurrentSession().get(c, id);
    }

    @Override
    public T get(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        List<T> l = q.list();
        if ((l != null) && (l.size() > 0)) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public T get(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if ((l != null) && (l.size() > 0)) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public void delete(T o) {
        if (o != null) {
            this.getCurrentSession().delete(o);
        }
    }

    @Override
    public void update(T o) {

        if (o != null) {
            this.getCurrentSession().update(o);
        }


    }

    @Override
    public void saveOrUpdate(T o) {
        if (o != null) {
            this.getCurrentSession().saveOrUpdate(o);
        }
    }

    @Override
    public List<T>find(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.list();
    }

    @Override
    public T findT(String hql, Map<String, Object> params){
        Query q = this.getCurrentSession().createQuery(hql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> tList = q.list();
        if(tList!=null&&tList.size()>0){
            return  tList.get(0);
        }else{
            return  null;
        }
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.list();
    }



    public Double getUniqueResult(String hql,Map<String, Object> params){
        Query q =  this.getCurrentSession().createQuery(hql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return (Double)q.uniqueResult();
    }

    public Double getUniqueResult(String hql){
        Query q =  this.getCurrentSession().createQuery(hql);
        return (Double)q.uniqueResult();
    }


    @Override
    public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
        Query q = this.getCurrentSession().createQuery(hql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<T> find(String hql, int page, int rows) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public Long count(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public Integer maxValue(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return (Integer) q.uniqueResult();
    }

    @Override
    public Long count(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return (Long) q.uniqueResult();
    }

    @Override
    public int executeHql(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.executeUpdate();
    }

    @Override
    public int executeHql(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.executeUpdate();
    }

    @Override
    public List<Object[]> findBySql(String sql) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.list();
    }

    @Override
    public List<Object[]> findBySql(String sql, int page, int rows) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.list();
    }

    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public int executeSql(String sql) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.executeUpdate();
    }

    @Override
    public int executeSql(String sql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.executeUpdate();
    }

    @Override
    public BigInteger countBySql(String sql) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return (BigInteger) q.uniqueResult();
    }

    @Override
    public BigInteger countBySql(String sql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return (BigInteger) q.uniqueResult();
    }

    @Override
    public Integer countBySqlToInt(String sql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        String count = q.uniqueResult().toString();
        if(count!=null){
            return  Integer.parseInt(count);
        }
        return  0;
    }

    @Override
    public Double countBySqlToDouble(String sql, Map<String, Object> params){
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        String count = q.uniqueResult().toString();
        if(count!=null){
            return  Double.parseDouble(count);
        }
        return  0.00;
    }
    @Override
    public List<Map<String, Object>> findAllListMapBySql(String sql,
                                                         Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    public List<T> findAllListBySql(String sql, Class c,
                                    Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if ((params != null) && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(c)).list();
    }

    @Override
    public T getBySQL(String sql, Class clz) {
        return (T) this.getCurrentSession().createSQLQuery(sql).addEntity(clz).uniqueResult();
    }

    @Override
    public List<Map<String, Object>> findListMapBySQL(String sql,
                                                      Map<String, Object> params) {
        Query query = getCurrentSession().createSQLQuery(sql);
        if (params != null) {
            Iterator<String> keyIt = params.keySet().iterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Object value = params.get(key);
                query.setParameter(key, value);
            }
        }
        return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }




    @Override
    public Grid getGridBySQLResultMap(String searchSQL, String countSQL,
                                      PageFilter page, Map<String, Object> params) {
        Grid grid = new Grid();
        Query rowQuery = this.getCurrentSession().createSQLQuery(searchSQL);
        Query countQuery = this.getCurrentSession().createSQLQuery(countSQL);

        String regEx = "\\:\\w+";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(searchSQL);
        while (mat.find()) {
            String key = mat.group().substring(1);
            if (params.containsKey(key)) {
                Object value = params.get(key);
                if (value != null && StringUtils.isNotEmpty(value.toString())) {
                    rowQuery.setParameter(key, value);
                    countQuery.setParameter(key, value);
                }
            }
        }
        grid.setRows(rowQuery.setFirstResult((page.getPage() - 1) * page.getRows()).setMaxResults(page.getRows()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
        if (null != countQuery.uniqueResult() && !("").equals(countQuery.uniqueResult().toString())) {
            grid.setTotal(Long.valueOf(countQuery.uniqueResult().toString()));
        } else {
            grid.setTotal(0l);
        }
        return grid;
    }

    @Override
    public Grid getGridByHQLResultMap(String searchHQL, String countHQL,
                                      PageFilter page, Map<String, Object> params) {
        Grid grid = new Grid();
        Query rowQuery = this.getCurrentSession().createQuery(searchHQL);
        Query countQuery = this.getCurrentSession().createQuery(countHQL);

        String regEx = "\\:\\w+";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(searchHQL);
        while (mat.find()) {
            String key = mat.group().substring(1);
            if (params.containsKey(key)) {
                Object value = params.get(key);
                if (value != null && StringUtils.isNotEmpty(value.toString())) {
                    rowQuery.setParameter(key, value);
                    countQuery.setParameter(key, value);
                }
            }
        }
        grid.setRows(rowQuery.setFirstResult((page.getPage() - 1) * page.getRows()).setMaxResults(page.getRows()).list());
        grid.setTotal(Long.valueOf(countQuery.uniqueResult().toString()));
        return grid;
    }
}
