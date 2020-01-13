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
	 * ������ҳ��ѯ
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
		//����������decidedzone����δnull��
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.finByCriteria(detachedCriteria);
	}
	/**
	 * 	���ݶ���ID�鿴��������
	 */
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//��ӹ�������
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		return subareaDao.finByCriteria(detachedCriteria);
	}
	/**
	 * ��������ֲ�highcharts����
	 */
	public List<Object> findSubareasGroupByProvince() {
		return subareaDao.findSubareasGroupByProvince();
	}

}
