package com.itzpx.bos.dao;

import java.util.List;

import com.itzpx.bos.dao.base.IBaseDao;
import com.itzpx.bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function>{

	public List<Function> findFunctionListByUserId(String id);

	public List<Function> findAllMenu();

	public List<Function> findMenuBuUserId(String id);

}
