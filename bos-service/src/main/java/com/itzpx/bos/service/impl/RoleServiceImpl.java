package com.itzpx.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itzpx.bos.dao.IRoleDao;
import com.itzpx.bos.domain.Function;
import com.itzpx.bos.domain.Role;
import com.itzpx.bos.service.IRoleService;
import com.itzpx.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private IRoleDao dao;
	/**
	 * �����ɫ������Ȩ��
	 */
	public void save(Role role,String functionIds) {
		dao.save(role);
		if (StringUtils.isNotBlank(functionIds)) {
			String[] fIds = functionIds.split(",");
			for (String functionId : fIds) {
				//�ֶ�����һ��Ȩ�޶�������id������״̬Ϊ�й�״̬
				Function function = new Function(functionId);
				role.getFunctions().add(function);
			}
		}
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	/**
	 * ���ؽ�ɫ����
	 */
	public List<Role> findAll() {
		return dao.findAll();
	}

}
