package com.itzpx.bos.service;

import com.itzpx.bos.domain.User;
import com.itzpx.bos.utils.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(String id, String password);

	public void save(User model, String[] roleIds);

	public void pageQuery(PageBean pageBean);

}
