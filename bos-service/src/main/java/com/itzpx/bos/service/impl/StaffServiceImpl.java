package com.itzpx.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itzpx.bos.dao.IStaffDao;
import com.itzpx.bos.domain.Staff;
import com.itzpx.bos.service.IStaffService;
import com.itzpx.bos.utils.PageBean;
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	@Autowired
	private IStaffDao staffDao;
	public void save(Staff model) {
		staffDao.save(model);
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}
	/**
	 * 取派员逻辑删除，将deltag改为1
	 */
	public void deleteBatch(String ids) {//1,2,3,4
		if (StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				staffDao.executeUpdate("staff.delete", id);
			}
		}
		
	}
	/**
	 * 跟据ID查询取派员
	 */
	public Staff findById(String id) {
		// TODO Auto-generated method stub
		return staffDao.findById(id);
	}
	/**
	 * 根据ID修改取派员
	 */
	public void update(Staff staff) {
		staffDao.update(staff);
		
	}
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		
		return staffDao.finByCriteria(detachedCriteria);
	}

}
