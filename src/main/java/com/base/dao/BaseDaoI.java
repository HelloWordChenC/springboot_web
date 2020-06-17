package com.base.dao;

import com.base.dao.until.Grid;
import com.base.dao.until.PageFilter;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface BaseDaoI<T> {

	public Serializable save(T o);

	public void delete(T o);

	public void update(T o);

	public void saveOrUpdate(T o);

	public T get(Class<T> c, Serializable id);

	public T get(String hql);
	
	public T getBySQL(String sql, Class clz);

	public T get(String hql, Map<String, Object> params);

	public List<T> find(String hql);

	public List<T> find(String hql, Map<String, Object> params);

	public T findT(String hql, Map<String, Object> params);


	public List<T> find(String hql, int page, int rows);

	public List<T> find(String hql, Map<String, Object> params, int page, int rows);

	public Long count(String hql);
	
	public Integer maxValue(String hql);

	public Long count(String hql, Map<String, Object> params);

	public int executeHql(String hql);

	public int executeHql(String hql, Map<String, Object> params);

	public List<Object[]> findBySql(String sql);

	public List<Object[]> findBySql(String sql, int page, int rows);

	public List<Object[]> findBySql(String sql, Map<String, Object> params);
	
	public List<Map<String,Object>> findAllListMapBySql(String sql, Map<String, Object> params);
	
	public List<T> findAllListBySql(String sql, Class c, Map<String, Object> params);

	public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows);
	
	public List<Map<String,Object>> findListMapBySQL(String sql, Map<String, Object> params);

	public int executeSql(String sql);

	public int executeSql(String sql, Map<String, Object> params);

	public BigInteger countBySql(String sql);

	public BigInteger countBySql(String sql, Map<String, Object> params);

	public Integer countBySqlToInt(String sql, Map<String, Object> params);

	public Double countBySqlToDouble(String sql, Map<String, Object> params);
	
	public Grid getGridBySQLResultMap(String searchSQL, String countSQL, PageFilter page, Map<String, Object> params);

	public Grid getGridByHQLResultMap(String searchHQL, String countHQL, PageFilter page, Map<String, Object> params);


	public Double getUniqueResult(String hql, Map<String, Object> params);

	public Double getUniqueResult(String hql);
}
