package com.sy.investment.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.sy.investment.domain.EntityBase;
import com.sy.investment.domain.entity.ParamsEntity;
import com.sy.investment.exceptions.DaoException;
import com.sy.investment.exceptions.ServiceException;
import com.sy.investment.reponsitory.CommonDao;
import com.sy.investment.service.CommonService;
import com.sy.investment.utils.ParamValue;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CommonServiceImpl implements CommonService{
	
	@Resource
	private  CommonDao  commonDao;

	@Override
	public <T extends EntityBase> void save(T t) throws ServiceException {
		commonDao.save(t);
		commonDao.flush();
	}

	@Override
	public <T extends EntityBase> void update(T t) throws ServiceException {
		commonDao.update(t);
		commonDao.flush();
	}

	@Override
	public <T extends EntityBase>  void delete(Class<T> clazz,Long id) throws ServiceException {
		commonDao.deleteById(clazz, id);
		commonDao.flush();
	}
	@Transactional(readOnly=true)
	@Override
	public <T extends EntityBase> T findEntityById(Class<T> clazz, Long id) throws ServiceException {
		commonDao.clear();
		return commonDao.findEntityById(clazz, id);
	}
	
	public <T extends EntityBase> void detach(T t) throws DaoException{
		commonDao.detach(t);
	}
	@Override
	public <T extends EntityBase> T findEntity(Class<T> clazz ,String hql, ParamValue params) throws ServiceException {
		try {
			return commonDao.findEntity(clazz, hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends EntityBase> List<T> findAll(Class<T> clazz, String hql, ParamValue params)
			throws ServiceException {
		try {
			return commonDao.findAll(clazz, hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends EntityBase>void updateByHql(Class<T> clazz, String hql, ParamValue params)
			throws ServiceException {
		try {
			commonDao.update(clazz, hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends EntityBase> Page<T> findPage(Class<T> clazz, String hql, ParamValue params, Pageable pageable)
			throws ServiceException {
		try {
			return commonDao.findPage(clazz, hql, params, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends EntityBase> Double getSum(Class<T> clazz,  String field,String hql,ParamValue params) throws ServiceException {
		try {
			return commonDao.getSum(clazz, field, hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends Object> List<T> findAllBySql(Class<T> clazz, String sql,ParamValue params) throws ServiceException {
		try {
			return commonDao.findAllBySql(clazz, sql,params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	@Override
	public List<Map<String, Object>> findMapsBySql(String sql,ParamValue params){
		return commonDao.findMapsBySql(sql, params);
	}

	@Override
	public <T extends Object> T findEntityBySql(Class<T> clazz, String sql, ParamValue params)
			throws ServiceException {
		try {
			return commonDao.findEntityBySql(clazz, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends Object> Page<T> findPageBySql(Class<T> clazz, String sql, ParamValue params,
			Pageable pageable) throws ServiceException {
		try {
			return commonDao.findPageBySql(clazz, sql, params, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends EntityBase> long count(Class<T> clazz, String hql, ParamValue params) throws ServiceException {
		try {
			return commonDao.count(clazz, hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends EntityBase> long countBySql(String sql, ParamValue params) throws ServiceException {
		try {
			return commonDao.countBySql(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <T> T executeFunc(Class<T> clazz, String name, ParamValue params) {
		try {
			return commonDao.executeFunc(clazz, name, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void executeSql(String sql, ParamValue param) {
		try {
			commonDao.executeSql(sql, param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public String[] executeProcs(String name, List<ParamsEntity> params) throws ServiceException {
		try {
			return commonDao.executeProcs(name, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
