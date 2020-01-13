package com.itzpx.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itzpx.bos.dao.IFunctionDao;
import com.itzpx.bos.domain.Function;
import com.itzpx.bos.domain.User;
import com.itzpx.bos.service.IFunctionService;
import com.itzpx.bos.utils.BosUtils;
import com.itzpx.bos.utils.PageBean;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	
	@Autowired
	private IFunctionDao dao;
	@Override
	public List<Function> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}
	/**
	 * ���Ȩ��
	 */
	public void save(Function model) {
		dao.save(model);
	}
	//Ȩ�ޱ��ҳ��ѯ����
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
		
	}
	/**
	 * ���ݵ�½�˲�ѯ�˵�������json����
	 */
	public List<Function> findMenu() {
		User user = BosUtils.getLoginUser();
		List<Function> list = null;
		if (user.getUsername().equals("admin")) {
			//����ǳ�������Ա����ѯ����Ȩ��
			list = dao.findAllMenu();
		}else {
			//��ͨ�û�
			list = dao.findMenuBuUserId(user.getId());
		}
		return list;
	}

}
