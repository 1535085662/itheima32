package com.itzpx.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itzpx.bos.dao.IDecidedzoneDao;
import com.itzpx.bos.dao.ISubareaDao;
import com.itzpx.bos.domain.Decidedzone;
import com.itzpx.bos.domain.Subarea;
import com.itzpx.bos.service.IDecidedzoneService;
import com.itzpx.bos.utils.PageBean;
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{
	@Autowired
	private ISubareaDao subareaDao;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	
	/**
	 * 	添加定区，关联分区
	 */
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);
			//model.getSubareas().add(subarea);一方（定区）,已经放弃维护外键权力，只能由多方（分区）来维护
			subarea.setDecidedzone(model);//分区关联定区
		}
	}

	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
