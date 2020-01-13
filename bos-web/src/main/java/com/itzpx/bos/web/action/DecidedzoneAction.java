package com.itzpx.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itzpx.bos.domain.Decidedzone;
import com.itzpx.bos.service.IDecidedzoneService;
import com.itzpx.bos.web.action.base.BaseAction;
import com.itzpx.crm.Customer;
import com.itzpx.crm.ICustomerService;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	//�������������ն������id
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	@Autowired
	private IDecidedzoneService decidedzoneService;
	
	/**
	 * 	��ӷ���
	 * @return
	 */
	public String add() {
		decidedzoneService.save(model,subareaid);
		return LIST;
	}
	/**
	 * 	��ҳ
	 */
	public String pageQuery() {
		decidedzoneService.pageQuery(pageBean);
		this.java2Json(pageBean,new String [] {"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
		return NONE;
	}
	@Autowired
	private ICustomerService customerService;
	/**
	 * 	��ȡcrm�����ṩ�Ŀͻ�����
	 * @return
	 */
	public String findListNotAssociation() {
		List<Customer> list = customerService.findListNotAssociation();
		this.java2Json(list, new String[] {});
		return NONE;
	}
	/**
	 * 	��ȡcrm�����ṩ�Ŀͻ�����
	 * @return
	 */
	public String findListHasAssociation() {
		String id = model.getId();
		List<Customer> list = customerService.findListHasAssociation(id);
		this.java2Json(list, new String[] {});
		return NONE;
	}
	//������������ҳ���ύ�Ŀͻ�ID
	private List<Integer> customerIds;
	
	/**
	 * 	�ͻ�����������
	 * @return
	 */
	public String assigncustomerstodecidedzone() {
		customerService.assigncustomerstodecidedzone(model.getId(), customerIds);
		return LIST;
	}
	public List<Integer> getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
}
