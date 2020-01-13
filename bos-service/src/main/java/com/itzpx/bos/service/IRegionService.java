package com.itzpx.bos.service;

import java.util.List;

import com.itzpx.bos.domain.Region;
import com.itzpx.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> regionList);

	public void pageQuery(PageBean pageBean);

	public List<Region> finAll();

	public List<Region> finListByq(String q);

}
