package com.itzpx.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itzpx.bos.utils.PageBean;

/**
 * 	�־ò�ͨ�ýӿ�
 * @author ����ү
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public T findById(Serializable id);//Serializable���л�
	public List<T> findAll();
	public List<T> finByCriteria(DetachedCriteria detachedCriteria);
	public void executeUpdate(String queryName,Object...object);
	public void pageQuery(PageBean pageBean);//ͨ�÷�ҳ��ѯ�ӿ�
}
