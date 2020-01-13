package com.itzpx.bos.service;

import java.util.List;

import com.itzpx.bos.domain.Role;
import com.itzpx.bos.utils.PageBean;

public interface IRoleService{

	public void save(Role model,String functionIds);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();
	
}
