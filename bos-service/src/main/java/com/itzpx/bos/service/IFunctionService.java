package com.itzpx.bos.service;

import java.util.List;

import com.itzpx.bos.domain.Function;
import com.itzpx.bos.utils.PageBean;

public interface IFunctionService {

	public List<Function> findAll();

	public void save(Function model);
	
	public void pageQuery(PageBean pageBean);

	public List<Function> findMenu();

}
