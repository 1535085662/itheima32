package com.itzpx.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itzpx.bos.dao.base.IBaseDao;
import com.itzpx.bos.utils.PageBean;
/**
 * 	�־ó�ͨ��ʵ��
 * @author ����ү
 *
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	//entityClassΪfindById��������
	private Class<T> entityClass;
	//�ֶ�ע��HibernateDaoSupportģ��
	//1.@Resource�ȿ��Ը��ݶ��������ע��Ҳ���Ը��ݶ��������ע�룬spring���ù�SessionFactory�����Կ���ע��
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	//��ȡentityClass����ֻҪ�̳�BaseDaoImpl�ͻ���øù��캯��
	public BaseDaoImpl() {
		//1.this�����������getClass()�����ȡ�������ͣ�getGenericSuperclass()�����ȡ���ุ������
		//Ҳ���ǻ�ȡBaseDaoImpl����<T>
		//Type Superclass = this.getClass().getGenericSuperclass();
		//Type����ǿתΪParameterizedType
		ParameterizedType Superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//2.��ȡ���������ķ�������<T,a,b,c>
		Type[] actualTypeArguments = Superclass.getActualTypeArguments();
		//3.��ֵ
		entityClass = (Class<T>) actualTypeArguments[0];
	}
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	//��ȡidʹ��get������Ҫ����entityClass(������ͣ��൱��User.class/.....)
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	//entityClass.getSimpleName()�൱��User
	public List<T> findAll() {
		//��������൱��FROM user
		String hql = "FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * ִ�и���
	 * queryName:hql�������,��domain/*.xml�ж�����䡣
	 * Object:����С�����
	 */
	public void executeUpdate(String queryName, Object... object) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		//ΪHQL����еģ���ֵ
		int i = 0;
		for (Object object2 : object) {
			query.setParameter(i++, object2);
		}
		//ִ�и���
		query.executeUpdate();
	}
	/**
	 * ͨ�÷�ҳ��ѯ����
	 */
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();//��ǰҳ��
		int pageSize = pageBean.getPageSize();//ÿ����ʾ�ļ�¼��
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();//��ѯ����
		//��ѯtotal---��������
		detachedCriteria.setProjection(Projections.rowCount());//ָ��hibernate��ܷ���SQL����ʽ--->select count(*)from bc_staff;
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		pageBean.setTotal(count.intValue());
		//��ѯrows---��ǰҳ��Ҫչʾ�����ݼ���
		detachedCriteria.setProjection(null);//ָ��hibernate��ܷ���SQL����ʽ--->select * from bc_staff;
		//ָ��hibernate��ܷ�װ����ķ�ʽ
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	/**
	 * ��������
	 */
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
		
	}

	/**
	 * 	ͨ��������ѯ����
	 */
	public List<T> finByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
