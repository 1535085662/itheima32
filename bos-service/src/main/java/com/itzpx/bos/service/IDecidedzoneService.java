package com.itzpx.bos.service;

import com.itzpx.bos.domain.Decidedzone;
import com.itzpx.bos.utils.PageBean;

public interface IDecidedzoneService{

	public void save(Decidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);

}
