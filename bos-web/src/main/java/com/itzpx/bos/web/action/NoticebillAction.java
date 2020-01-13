package com.itzpx.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itzpx.bos.domain.Noticebill;
import com.itzpx.bos.service.INoticebillService;
import com.itzpx.bos.web.action.base.BaseAction;
import com.itzpx.crm.Customer;
/**
 * ҵ��֪ͨ��
 * @author ����ү
 *
 */
import com.itzpx.crm.ICustomerService;
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	//ע��crm����
	@Autowired
	private ICustomerService customerService;
	/**
	 * Զ�̵���crm���񣬸����ֻ��Ų�ѯ�ͻ���Ϣ
	 */
	public String findCustomerByTelephone() {
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		this.java2Json(customer, new String[] {});
		return NONE;
	}
	@Autowired
	private INoticebillService noticebillService;
	/**
	 * ����һ��ҵ��֪ͨ�����������Զ��ֵ�
	 */
	public String add() {
		noticebillService.save(model);
		return "noticebill_add";
	}
}
