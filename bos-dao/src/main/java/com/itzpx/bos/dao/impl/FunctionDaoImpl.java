package com.itzpx.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itzpx.bos.dao.IFunctionDao;
import com.itzpx.bos.dao.base.impl.BaseDaoImpl;
import com.itzpx.bos.domain.Function;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {
	public List<Function> findAll(){
		String hql = "FROM Function f WHERE	f.parentFunction IS	NULL";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 	�����û�id��ѯ��ӦȨ��
	 */
	public List<Function> findFunctionListByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id=?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

	/**
	 * admin�û��˵�
	 */
	public List<Function> findAllMenu() {
		String hql = "FROM Function f WHERE	f.generatemenu = '1' ORDER BY f.zindex DESC";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * ��ͨ�û��˵�
	 */
	public List<Function> findMenuBuUserId(String userId) {
		String hql = "SELECT DISTINCT f "
				+ "FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u "
				+ "WHERE u.id=? AND f.generatemenu = '1' ORDER BY f.zindex DESC";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

}
