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
	//属性驱动，接收多个分区id
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	@Autowired
	private IDecidedzoneService decidedzoneService;
	
	/**
	 * 	添加分区
	 * @return
	 */
	public String add() {
		decidedzoneService.save(model,subareaid);
		return LIST;
	}
	/**
	 * 	分页
	 */
	public String pageQuery() {
		decidedzoneService.pageQuery(pageBean);
		this.java2Json(pageBean,new String [] {"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
		return NONE;
	}
	@Autowired
	private ICustomerService customerService;
	/**
	 * 	获取crm服务提供的客户数据
	 * @return
	 */
	public String findListNotAssociation() {
		List<Customer> list = customerService.findListNotAssociation();
		this.java2Json(list, new String[] {});
		return NONE;
	}
	/**
	 * 	获取crm服务提供的客户数据
	 * @return
	 */
	public String findListHasAssociation() {
		String id = model.getId();
		List<Customer> list = customerService.findListHasAssociation(id);
		this.java2Json(list, new String[] {});
		return NONE;
	}
	//属性驱动接收页面提交的客户ID
	private List<Integer> customerIds;
	
	/**
	 * 	客户关联到定区
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
