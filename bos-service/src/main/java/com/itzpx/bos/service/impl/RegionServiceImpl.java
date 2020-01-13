package com.itzpx.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itzpx.bos.dao.IRegionDao;
import com.itzpx.bos.domain.Region;
import com.itzpx.bos.service.IRegionService;
import com.itzpx.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService{
	@Autowired
	private IRegionDao regionDao;
	/**
	 * ����������������
	 */
	public void saveBatch(List<Region> regionList) {
		for (Region region : regionList) {
			regionDao.saveOrUpdate(region);
		}
	}
	/**
	 * 	�����ҳ��ѯ
	 */
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}
	
	public List<Region> finAll() {
		return regionDao.findAll();
	}
	/**
	 *	 ����ҳ������ģ����ѯ
	 */
	public List<Region> finListByq(String q) {
		// TODO Auto-generated method stub
		return regionDao.finListByq(q);
	}

}
