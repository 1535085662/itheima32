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
	 * ���ȡ��Ա����
	 */
	public String add() {
		staffService.save(model);
		return LIST;
	}
	//����ע�����ҳ�洫�ݹ�����page��rows.
//	private int page;
//	private int rows;
	/**
	 * 	ע�͵��Ĵ��붼����ȡ��BaseAction��
	 * 	��ҳ��ѯ����
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException {
//		PageBean pageBean = new PageBean();
//		pageBean.setCurrentPage(page);
//		pageBean.setPageSize(rows);
		//���������ύ��ѯ����
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);//��Staff���ݹ�ȥ
//		pageBean.setDetachedCriteria(detachedCriteria);
		staffService.pageQuery(pageBean);
		//ʹ��json-lib,pageBean���������Ը�ֵ��Ϻ󣬽����װΪjson���ݣ�����ҳ��
		//JSONObject����һ�Ķ���ת��Ϊjson
		//JSONArray������򼯺϶���ת��Ϊjson
		//����json���м������Բ���Ҫ���ݵ�ҳ���У�ʹ��JsonConfigȥ������Ҫ������
		this.java2Json(pageBean,new String [] {"decidedzones","currentPage","detachedCriteria","pageSize"});	//����java2Json������ת������Ӧ��ҳ��
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.setExcludes(new String [] {"currentPage","detachedCriteria","pageSize"});
//		String json = JSONObject.fromObject(pageBean,jsonConfig).toString();
//		ServletActionContext.getResponse().setContentType("text/json;charset=utf8");
//		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	
	//��������������ҳ���ύ��ids
	private String ids;
	/**
	 * ����ɾ��
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
	 * �޸�ȡ��Ա
	 */
	public String edit() {
		//ʹ��ID��ѯԭʼ����
		Staff staff = staffService.findById(model.getId());
		//ʹ��ҳ���ύ���ݸ���ԭʼ����
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return LIST;
	}
	/**
	 * 	��������������ѯ
	 */
	public String listajax() {
		List<Staff> list = staffService.findListNotDelete();
		this.java2Json(list, new String[] {"decidedzones"});
		return NONE;
	}
}
