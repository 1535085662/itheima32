package com.itzpx.bos.service;

import java.util.List;

import com.itzpx.bos.domain.Subarea;
import com.itzpx.bos.utils.PageBean;

public interface ISubareaService{

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> finAll();

	public List<Subarea> findListNotAssociation();

	public List<Subarea> findListByDecidedzoneId(String decidedzoneId);

	public List<Object> findSubareasGroupByProvince();

}
