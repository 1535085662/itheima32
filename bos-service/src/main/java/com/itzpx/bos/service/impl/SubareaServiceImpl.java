package com.itzpx.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itzpx.bos.dao.ISubareaDao;
import com.itzpx.bos.domain.Subarea;
import com.itzpx.bos.service.ISubareaService;
import com.itzpx.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService{
	@Autowired
	private ISubareaDao subareaDao;
	public void save(Subarea model) {
		subareaDao.save(model);
	}
	/**
	 * 定区分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
		
	}
	@Override
	public List<Subarea> finAll() {
		return subareaDao.findAll();
	}
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//分区对象中decidedzone属性未null的
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.finByCriteria(detachedCriteria);
	}
	/**
	 * 	根据定区ID查看关联分区
	 */
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		return subareaDao.finByCriteria(detachedCriteria);
	}
	/**
	 * 返回区域分布highcharts数据
	 */
	public List<Object> findSubareasGroupByProvince() {
		return subareaDao.findSubareasGroupByProvince();
	}

}
