package com.itzpx.bos.dao;

import java.util.List;

import com.itzpx.bos.dao.base.IBaseDao;
import com.itzpx.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region>{

	public List<Region> finListByq(String q);

}
