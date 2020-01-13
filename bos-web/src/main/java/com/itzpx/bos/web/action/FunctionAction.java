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
	 *	 ��ѯ����Ȩ�ޣ�����json����
	 * @return
	 */
	public String listajax() {
		List<Function> list = service.findAll();
		this.java2Json(list, new String[] {"parentFunction","roles"});
		return NONE;
	}
	/**
	 * 	���Ȩ��
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
	//Ȩ�޷�ҳ��ѯ
	public String pageQuery() {
		//����pageBean��װ��page������function��װ��page���Գ�ͻ
		//ǰ�˷�ҳ�����ݹ�����page�����ݵ�function��page��
		//���Խ�function�е�pageȡ��������pageBean��page
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		service.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"parentFunction","roles","children"});
		return NONE;
	}
	/**
	 * ���ݵ�½�˲�ѯ�˵�������json����
	 */
	public String findMenu() {
		List<Function> list = service.findMenu();
		this.java2Json(list, new String[] {"parentFunction","roles","children"});
		return NONE;
	}
}
