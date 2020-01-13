package com.itzpx.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itzpx.bos.dao.IRegionDao;
import com.itzpx.bos.dao.base.impl.BaseDaoImpl;
import com.itzpx.bos.domain.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao{

	/**
	 * 	跟据q进行模糊查询
	 */
	public List<Region> finListByq(String q) {
		String hql = "FROM Region r WHERE r.shortcode LIKE ? OR r.citycode LIKE ? OR r.province LIKE ? OR r.city LIKE ? OR r.district LIKE ?";
		List<Region> list =(List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
		return list;
	}

}
