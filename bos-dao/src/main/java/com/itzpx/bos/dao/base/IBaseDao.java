package com.itzpx.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itzpx.bos.utils.PageBean;

/**
 * 	持久层通用接口
 * @author 曾大爷
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public T findById(Serializable id);//Serializable序列化
	public List<T> findAll();
	public List<T> finByCriteria(DetachedCriteria detachedCriteria);
	public void executeUpdate(String queryName,Object...object);
	public void pageQuery(PageBean pageBean);//通用分页查询接口
}
