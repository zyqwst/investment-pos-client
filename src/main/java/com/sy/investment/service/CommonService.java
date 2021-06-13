package com.sy.investment.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sy.investment.domain.EntityBase;
import com.sy.investment.domain.entity.ParamsEntity;
import com.sy.investment.exceptions.DaoException;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.utils.ParamValue;

public interface CommonService {
	public <T extends EntityBase> void save(T t) throws ServiceException;
	
	public <T extends EntityBase>  void update(T t) throws ServiceException;
	
	public <T extends EntityBase>  void delete(Class<T> clazz,Long id) throws ServiceException;
	
	public <T extends EntityBase> T findEntityById(Class<T> clazz,Long id) throws ServiceException;
	
	public <T extends EntityBase> void detach(T t) throws DaoException;
	
	public <T extends EntityBase> T findEntity(Class<T> clazz ,String hql,ParamValue params) throws ServiceException;
	
	public<T extends EntityBase> List<T> findAll(Class<T> clazz ,String hql,ParamValue params) throws ServiceException;
	
	public <T extends EntityBase> void updateByHql(Class<T> clazz, String hql, ParamValue params) throws ServiceException;
	public <T extends EntityBase> Page<T> findPage(Class<T> clazz, String hql, ParamValue params, Pageable pageable)
			throws ServiceException;
	
	public <T extends EntityBase> Double getSum(Class<T> clazz,String field,String hql,ParamValue params) throws ServiceException;
	public <T extends EntityBase> long count(Class<T> clazz,String hql,  ParamValue params) throws ServiceException;
	public <T extends EntityBase> long countBySql(String sql,  ParamValue params) throws ServiceException;
	public <T extends Object> List<T> findAllBySql(Class<T> clazz,String sql,ParamValue params) throws ServiceException;
	
	public <T extends Object> T findEntityBySql(Class<T> clazz,String sql,ParamValue params) throws ServiceException;
	
	public <T extends Object> Page<T> findPageBySql(Class<T> clazz,String sql,ParamValue params,Pageable pageable) throws ServiceException;
	
	public <T extends Object> T  executeFunc(Class<T> clazz,String name,ParamValue params) throws ServiceException;
	public void executeSql(String sql,ParamValue param) throws ServiceException;
	public String[] executeProcs(String name,List<ParamsEntity> params)throws ServiceException;

	List<Map<String, Object>> findMapsBySql(String sql, ParamValue params);
}
