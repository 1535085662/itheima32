package com.itzpx.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itzpx.bos.domain.Function;
import com.itzpx.bos.service.IFunctionService;
import com.itzpx.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	@Autowired
	private IFunctionService service;
	
	/**
	 *	 查询所有权限，返回json数据
	 * @return
	 */
	public String listajax() {
		List<Function> list = service.findAll();
		this.java2Json(list, new String[] {"parentFunction","roles"});
		return NONE;
	}
	/**
	 * 	添加权限
	 * @return
	 */
	public String add() {
		Function function = model.getParentFunction();
		if (function !=null && function.getId().equals("")) {
			model.setParentFunction(null);
		}
		service.save(model);
		return LIST;
	}
	//权限分页查询
	public String pageQuery() {
		//由于pageBean封装的page属性与function封装的page属性冲突
		//前端分页，传递过来的page被传递到function的page中
		//所以将function中的page取出，赋予pageBean的page
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"parentFunction","roles","children"});
		return NONE;
	}
	/**
	 * 根据登陆人查询菜单，返回json数据
	 */
	public String findMenu() {
		List<Function> list = service.findMenu();
		this.java2Json(list, new String[] {"parentFunction","roles","children"});
		return NONE;
	}
}
