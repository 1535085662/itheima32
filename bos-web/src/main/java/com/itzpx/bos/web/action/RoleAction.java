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
	//��������������Ȩ��Id
	private String functionIds;
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	//��ɫ���
	public String add() {
		roleService.save(model,functionIds);
		return LIST;
	}
	/**
	 * ��ɫ��ҳ��ѯ
	 * @return
	 */
	public String pageQuery() {
		roleService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"functions","users"});
		return NONE;
	}
	/**
	 * ���ؽ�ɫ����
	 * @return
	 */
	public String listajax() {
		List<Role> list = roleService.findAll();
		this.java2Json(list, new String[] {"functions","users"});
		return NONE;
	}
}
