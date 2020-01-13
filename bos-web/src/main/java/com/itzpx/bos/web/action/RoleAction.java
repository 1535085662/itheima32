package com.itzpx.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itzpx.bos.domain.Role;
import com.itzpx.bos.service.IRoleService;
import com.itzpx.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	@Autowired
	private IRoleService roleService;
	//属性驱动，接受权限Id
	private String functionIds;
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	//角色添加
	public String add() {
		roleService.save(model,functionIds);
		return LIST;
	}
	/**
	 * 角色分页查询
	 * @return
	 */
	public String pageQuery() {
		roleService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"functions","users"});
		return NONE;
	}
	/**
	 * 加载角色数据
	 * @return
	 */
	public String listajax() {
		List<Role> list = roleService.findAll();
		this.java2Json(list, new String[] {"functions","users"});
		return NONE;
	}
}
