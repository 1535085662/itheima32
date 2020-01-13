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
	 * 保存角色，关联权限
	 */
	public void save(Role role,String functionIds) {
		dao.save(role);
		if (StringUtils.isNotBlank(functionIds)) {
			String[] fIds = functionIds.split(",");
			for (String functionId : fIds) {
				//手动构造一个权限对象，设置id，对象状态为托管状态
				Function function = new Function(functionId);
				role.getFunctions().add(function);
			}
		}
	}
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}
	/**
	 * 加载角色数据
	 */
	public List<Role> findAll() {
		return dao.findAll();
	}

}
