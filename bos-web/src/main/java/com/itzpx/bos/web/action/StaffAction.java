package com.itzpx.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itzpx.bos.domain.Staff;
import com.itzpx.bos.service.IStaffService;
import com.itzpx.bos.web.action.base.BaseAction;


@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	@Autowired
	private IStaffService staffService;
	/**
	 * 添加取派员方法
	 */
	public String add() {
		staffService.save(model);
		return LIST;
	}
	//属性注入接收页面传递过来的page、rows.
//	private int page;
//	private int rows;
	/**
	 * 	注释掉的代码都是提取到BaseAction中
	 * 	分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException {
//		PageBean pageBean = new PageBean();
//		pageBean.setCurrentPage(page);
//		pageBean.setPageSize(rows);
		//创建离线提交查询对象
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);//将Staff传递过去
//		pageBean.setDetachedCriteria(detachedCriteria);
		staffService.pageQuery(pageBean);
		//使用json-lib,pageBean对象中属性赋值完毕后，将其封装为json数据，返回页面
		//JSONObject将单一的对象转化为json
		//JSONArray将数组或集合对象转化为json
		//现在json中有几个属性不需要传递到页面中，使用JsonConfig去除不需要的属性
		this.java2Json(pageBean,new String [] {"decidedzones","currentPage","detachedCriteria","pageSize"});	//调用java2Json做数据转化并响应到页面
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.setExcludes(new String [] {"currentPage","detachedCriteria","pageSize"});
//		String json = JSONObject.fromObject(pageBean,jsonConfig).toString();
//		ServletActionContext.getResponse().setContentType("text/json;charset=utf8");
//		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	
	//属性驱动，接受页面提交的ids
	private String ids;
	/**
	 * 批量删除
	 */
	@RequiresPermissions("staff-delete")
	public String deleteBatch() {
		staffService.deleteBatch(ids);
		return LIST;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * 修改取派员
	 */
	public String edit() {
		//使用ID查询原始数据
		Staff staff = staffService.findById(model.getId());
		//使用页面提交数据覆盖原始数据
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return LIST;
	}
	/**
	 * 	根据任意条件查询
	 */
	public String listajax() {
		List<Staff> list = staffService.findListNotDelete();
		this.java2Json(list, new String[] {"decidedzones"});
		return NONE;
	}
}
