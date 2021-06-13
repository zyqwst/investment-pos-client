package com.sy.investment.reponsitory.impl;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sy.investment.domain.EntityBase;
import com.sy.investment.domain.entity.ParamsEntity;
import com.sy.investment.reponsitory.CommonDao;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.ParamValue.Param;
@Repository
public  class  CommonDaoImpl  implements CommonDao{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public <T extends EntityBase> void save(T t)  {
		em.persist(t);
	}

	@Override
	public <T extends EntityBase> void update(T t)  {
		em.merge(t);
		em.flush();
	}

	@Override
	public  <T extends EntityBase>  void deleteById(Class<T> clazz,Long id)  {
		em.remove(findEntityById(clazz, id));
	}

	@Override
	public <T extends EntityBase> T findEntityById(Class<T> clazz, Long id)  {
		return em.find(clazz, id);
	}
	@Override
	public <T extends EntityBase> T findEntityByIdForUpdate(Class<T> clazz, Long id)  {
		return em.find(clazz, id,LockModeType.PESSIMISTIC_WRITE);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends EntityBase> T findEntity(Class<T> clazz ,String hql, ParamValue params)  {
		try {
			Query query = em.createQuery(" from "+clazz.getName()+" "+hql);
			if(params!=null) {
				for(Param p : params.get()){
					query.setParameter(p.getKey(),p.getValue());
				}
			}
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends EntityBase> List<T> findAll(Class<T> clazz ,String hql, ParamValue params)  {
		try {
			Query  query = em.createQuery(" FROM " + clazz.getName() + " "+hql );
			if(params!=null){
				for(Param p : params.get()){
					query.setParameter(p.getKey(),p.getValue());
				}
			}
			return  (List<T>) query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}  
	}
	@Override
	public <T extends EntityBase> void delete(T t)  {
		em.remove(t);
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public <T extends EntityBase> void update(Class<T> clazz, String hql, ParamValue params)  {
		Query  query = em.createQuery(" update " + clazz.getName() +" "+ hql );
		if(params!=null){
			for(Param p : params.get()){
				query.setParameter(p.getKey(),p.getValue());
			}
		}
		query.executeUpdate();
	}
	@Override
	public <T extends EntityBase> void delete(Class<T> clazz, String hql, ParamValue params)  {
		Query  query = em.createQuery(" delete " + clazz.getName() +" "+ hql );
		if(params!=null){
			for(Param p : params.get()){
				query.setParameter(p.getKey(),p.getValue());
			}
		}
		query.executeUpdate();
	}
	@Override
	public <T extends EntityBase> long count(Class<T> clazz,String hql,  ParamValue params)  {
        Query query = em.createQuery("select count(*) from "+clazz.getName()+" "+hql);
        if(params!=null){
			for(Param p : params.get()){
				query.setParameter(p.getKey(),p.getValue());
			}
		}
        return Long.parseLong(query.getSingleResult().toString()) ;
    }
	@Override
	public <T extends EntityBase> long countBySql(String sql,  ParamValue params)  {
        Query query = em.createNativeQuery(sql);
        if(params!=null){
        	for(int i = 0;i<params.get().size();i++){
				query.setParameter(i+1, params.get().get(i));
			}
		}
        Object o = query.getSingleResult();
        return Long.parseLong(o.toString());
    }
	@Override
	public <T extends EntityBase> Double getSum(Class<T> clazz,String field,String hql, ParamValue params)  {
		Query  query = em.createQuery("select sum("+field+") FROM " + clazz.getName() +" "+ hql );
		if(params!=null){
			for(Param p : params.get()){
				query.setParameter(p.getKey(),p.getValue());
			}
		}
		return  (Double) query.getSingleResult();         
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public <T extends Object> List<T> findAllBySql(Class<T> clazz,String sql,ParamValue params) {
		try {
			Query query = null;
			if(isJavaClass(clazz)) {
				query = em.createNativeQuery(sql);
			}else {
				query = em.createNativeQuery(sql, clazz);
			}
			if(params!=null){
				for(Param p : params.get()){
					query.setParameter(p.getKey(),p.getValue());
				}
			}
			return query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}  
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Map<String, Object>> findMapsBySql(String sql,ParamValue params) {
		try {
			Query query = null;
			
			query = em.createNativeQuery(sql);
			if(params!=null){
				for(Param p : params.get()){
					query.setParameter(p.getKey(),p.getValue());
				}
			}
			query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}  
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T findEntityBySql(Class<T> clazz, String sql, ParamValue params)
			 {
			Query query = null;
			if(isJavaClass(clazz)) {
				query = em.createNativeQuery(sql);
			}else {
				query = em.createNativeQuery(sql, clazz);
			}
			if(params!=null){
				for(Param p : params.get()){
					query.setParameter(p.getKey(),p.getValue());
				}
			}
			return (T) query.getSingleResult();
	}

	@Override
	public <T extends Object> Page<T> findPageBySql(Class<T> clazz, String sql, ParamValue params,
			Pageable pageable)  {
		Query query = null;
		if(isJavaClass(clazz)) {
			query = em.createNativeQuery(sql);
		}else {
			query = em.createNativeQuery(sql, clazz);
		}
		query.setFirstResult((int)pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		if(params!=null){
			for(Param p : params.get()){
				query.setParameter(p.getKey(),p.getValue());
			}
		}
		@SuppressWarnings("unchecked")
		List<T> contents = query.getResultList();
		if (pageable == null || pageable.getOffset() == 0) {

			if (pageable == null || pageable.getPageSize() > contents.size()) {
				return new PageImpl<T>(contents, pageable, contents.size());
			}

			return new PageImpl<T>(contents, pageable,countBySql("select count(1) "+sql.substring(sql.toUpperCase().indexOf("FROM")), params));
		}

		if (contents.size() != 0 && pageable.getPageSize() > contents.size()) {
			return new PageImpl<T>(contents, pageable, pageable.getOffset() + contents.size());
		}
		return new PageImpl<>(contents, pageable, contents.size());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EntityBase> Page<T> findPage(Class<T> clazz, String hql, ParamValue params, Pageable pageable)
			 {
		Query  query = em.createQuery(" FROM " + clazz.getName() + " "+hql );
		query.setFirstResult((int)pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		if(params!=null){
			for(Param p : params.get()){
				query.setParameter(p.getKey(),p.getValue());
			}
		}
		List<T> contents = query.getResultList();
		return new PageImpl<T>(contents, pageable, count(clazz, hql, params));
	}
	public <T extends EntityBase> void detach(T t)  {
		em.detach(t);
	}
	private  boolean isJavaClass(Class<?> clz) {    
	    return clz != null && clz.getClassLoader() == null;    
	}
	
	public String[] executeProcs(String name,List<ParamsEntity> params) {
		String[] rtn = new String[3];
		StoredProcedureQuery query = em.createStoredProcedureQuery(name);
		
		setParams(query, params);
		
		query.registerStoredProcedureParameter(params.size()+1, Integer.class, ParameterMode.OUT)
			 .registerStoredProcedureParameter(params.size()+2, String.class, ParameterMode.OUT)
			 .registerStoredProcedureParameter(params.size()+3, String.class, ParameterMode.OUT);
		
		rtn[0] = (int)query.getOutputParameterValue(params.size()+1)+"";
		rtn[1]= (String) query.getOutputParameterValue(params.size()+2);
		rtn[2]= (String) query.getOutputParameterValue(params.size()+3);
		
		return rtn;
	}
	@SuppressWarnings("unchecked")
	public <T extends Object> T  executeFunc(Class<T> clazz,String name,ParamValue params) {
		Query query;
		if(isJavaClass(clazz)) {
			query = em.createNativeQuery(name);
		}else {
			query = em.createNativeQuery(name, clazz);
		}
		if(params!=null)
		for(Param p : params.get()){
			query.setParameter(p.getKey(),p.getValue());
		}
		
		return  (T) query.getSingleResult();
	}
	private void setParams(StoredProcedureQuery query,List<ParamsEntity> params)  {
		int i=1;
	    for(ParamsEntity s:params) {
	    	if(s.getType()==Types.VARCHAR) {
	    		query.registerStoredProcedureParameter(i, String.class, ParameterMode.IN);
	    		
	        }
	        if(s.getType()==Types.INTEGER) {
	        	query.registerStoredProcedureParameter(i, Integer.class, ParameterMode.IN);
	        }
	        if(s.getType()==Types.DOUBLE) {
	        	query.registerStoredProcedureParameter(i, Double.class, ParameterMode.IN);
	        }
	        if(s.getType()==Types.BIGINT) {
	        	query.registerStoredProcedureParameter(i, Long.class, ParameterMode.IN);
	        }
	        query.setParameter(i++,  s.getValue());
	    }
	}

	@Override
	public void executeSql(String sql, ParamValue params)  {
		Query query = em.createNativeQuery(sql);
		if(params!=null){
			for(Param p : params.get()){
				query.setParameter(p.getKey(),p.getValue());
			}
		}
		query.executeUpdate();
	}
	@Override
	public void clear() {
		em.clear();
	}
}
