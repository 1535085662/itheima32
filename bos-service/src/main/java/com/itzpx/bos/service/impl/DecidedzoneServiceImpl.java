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
	 * 	��Ӷ�������������
	 */
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);
			//model.getSubareas().add(subarea);һ����������,�Ѿ�����ά�����Ȩ����ֻ���ɶ෽����������ά��
			subarea.setDecidedzone(model);//������������
		}
	}

	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
