package com.sy.investment.reponsitory;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sy.investment.domain.EntityBase;
import com.sy.investment.domain.entity.ParamsEntity;
import com.sy.investment.utils.ParamValue;

public interface  CommonDao{
	public<T extends EntityBase> void save(T t) ;
	
	public<T extends EntityBase>  void update(T t) ;
	
	public  <T extends EntityBase>  void deleteById(Class<T> clazz,Long id) ;
	
	public <T extends EntityBase> void delete(T t) ;
	/**
	 * 根据主键查询实体，并添加行级锁
	 * @param clazz
	 * @param id
	 * @return
	 */
	public<T extends EntityBase>  T findEntityByIdForUpdate(Class<T> clazz,Long id) ; 
	
	public<T extends EntityBase>  T findEntityById(Class<T> clazz,Long id) ;
	
	public<T extends EntityBase> T findEntity(Class<T> clazz ,String hql,ParamValue param) ;
	
	public<T extends EntityBase> List<T> findAll(Class<T> clazz ,String hql,ParamValue param) ;
	
	public void flush();
	
	public <T extends EntityBase> void detach(T t) ;
	
	public <T extends EntityBase> void update(Class<T> clazz,String hql,ParamValue param) ;
	
	public <T extends EntityBase> void delete(Class<T> clazz,String hql,ParamValue param) ;
	public <T extends EntityBase> long count(Class<T> clazz,String hql,  ParamValue param) ;
	public <T extends EntityBase> long countBySql(String sql,  ParamValue param) ;
	public <T extends EntityBase> Double getSum(Class<T> clazz,String field,String hql, ParamValue param)  ;
	public <T extends Object> List<T> findAllBySql(Class<T> clazz,String sql,ParamValue param) ;
	public <T extends Object> T findEntityBySql(Class<T> clazz,String sql,ParamValue param) ;
	public <T extends Object> Page<T> findPageBySql(Class<T> clazz, String sql, ParamValue param,Pageable pageable)  ;
	public <T extends EntityBase> Page<T> findPage(Class<T> clazz, String hql, ParamValue param,Pageable pageable)  ;

	public String[] executeProcs(String name,List<ParamsEntity> params) ;
	public <T extends Object> T  executeFunc(Class<T> clazz,String name,ParamValue params) ;
	
	public void executeSql(String sql,ParamValue param) ;
	public void clear();

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findMapsBySql(String sql, ParamValue params);
	
}
