package com.itzpx.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itzpx.bos.dao.ISubareaDao;
import com.itzpx.bos.dao.base.impl.BaseDaoImpl;
import com.itzpx.bos.domain.Subarea;
@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements ISubareaDao{

	@Override
	public List<Object> findSubareasGroupByProvince() {
		String hql = "SELECT r.province,count(*) FROM Subarea s LEFT OUTER JOIN s.region r Group BY r.province";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}

}
