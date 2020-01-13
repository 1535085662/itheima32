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
 * 	持久成通用实现
 * @author 曾大爷
 *
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	//entityClass为findById方法服务
	private Class<T> entityClass;
	//手动注入HibernateDaoSupport模板
	//1.@Resource既可以根据对象的名称注入也可以根据对象的名称注入，spring配置过SessionFactory，所以可以注入
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	//获取entityClass子类只要继承BaseDaoImpl就会调用该构造函数
	public BaseDaoImpl() {
		//1.this代表调用者类getClass()代表获取该类类型，getGenericSuperclass()代表获取该类父类类型
		//也就是获取BaseDaoImpl类型<T>
		//Type Superclass = this.getClass().getGenericSuperclass();
		//Type向下强转为ParameterizedType
		ParameterizedType Superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//2.获取父类声明的泛型数组<T,a,b,c>
		Type[] actualTypeArguments = Superclass.getActualTypeArguments();
		//3.赋值
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

	//获取id使用get方法需要传入entityClass(类的类型，相当于User.class/.....)
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	//entityClass.getSimpleName()相当于User
	public List<T> findAll() {
		//整条语句相当于FROM user
		String hql = "FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * 执行更新
	 * queryName:hql语句名称,在domain/*.xml中定义语句。
	 * Object:语句中“？”
	 */
	public void executeUpdate(String queryName, Object... object) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		//为HQL语句中的？赋值
		int i = 0;
		for (Object object2 : object) {
			query.setParameter(i++, object2);
		}
		//执行更新
		query.executeUpdate();
	}
	/**
	 * 通用分页查询方法
	 */
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();//当前页码
		int pageSize = pageBean.getPageSize();//每条显示的记录数
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();//查询条件
		//查询total---总数据量
		detachedCriteria.setProjection(Projections.rowCount());//指定hibernate框架发送SQL的形式--->select count(*)from bc_staff;
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		pageBean.setTotal(count.intValue());
		//查询rows---当前页需要展示的数据集合
		detachedCriteria.setProjection(null);//指定hibernate框架发送SQL的形式--->select * from bc_staff;
		//指定hibernate框架封装对象的方式
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	/**
	 * 保存或更新
	 */
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
		
	}

	/**
	 * 	通用条件查询方法
	 */
	public List<T> finByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
