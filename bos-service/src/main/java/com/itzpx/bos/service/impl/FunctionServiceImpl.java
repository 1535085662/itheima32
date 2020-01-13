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
	 * 添加权限
	 */
	public void save(Function model) {
		dao.save(model);
	}
	//权限表分页查询方法
	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
		
	}
	/**
	 * 根据登陆人查询菜单，返回json数据
	 */
	public List<Function> findMenu() {
		User user = BosUtils.getLoginUser();
		List<Function> list = null;
		if (user.getUsername().equals("admin")) {
			//如果是超级管理员，查询所有权限
			list = dao.findAllMenu();
		}else {
			//普通用户
			list = dao.findMenuBuUserId(user.getId());
		}
		return list;
	}

}
