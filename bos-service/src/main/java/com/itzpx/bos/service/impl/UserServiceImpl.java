package com.itzpx.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itzpx.bos.dao.IUserDao;
import com.itzpx.bos.domain.Role;
import com.itzpx.bos.domain.User;
import com.itzpx.bos.service.IUserService;
import com.itzpx.bos.utils.MD5Utils;
import com.itzpx.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements IUserService{
	//@Autowired由sping工厂创建对象
	@Autowired
	private IUserDao userDao;
	public User login(User user) {
		//md5加密
		String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(),password);
	}
	/**
	 * 根据用户id修改密码
	 */
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password,id);
	}
	/**
	 * 添加用户，并关联角色
	 */
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);
		if(roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				//手动构造托管对象
				Role role = new Role(roleId);
				//用户对象关联角色对象
				user.getRoles().add(role);
			}
		}
	}
	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

}
