package com.itzpx.bos.dao;

import java.util.List;

import com.itzpx.bos.dao.base.IBaseDao;
import com.itzpx.bos.domain.Subarea;

public interface ISubareaDao extends IBaseDao<Subarea>{

	public List<Object> findSubareasGroupByProvince();

}
