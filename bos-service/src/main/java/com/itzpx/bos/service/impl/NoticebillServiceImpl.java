package com.itzpx.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itzpx.bos.dao.IDecidedzoneDao;
import com.itzpx.bos.dao.INoticebillDao;
import com.itzpx.bos.dao.IWorkbillDao;
import com.itzpx.bos.domain.Decidedzone;
import com.itzpx.bos.domain.Noticebill;
import com.itzpx.bos.domain.Staff;
import com.itzpx.bos.domain.User;
import com.itzpx.bos.domain.Workbill;
import com.itzpx.bos.service.INoticebillService;
import com.itzpx.bos.utils.BosUtils;
import com.itzpx.crm.ICustomerService;
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService {
	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;
	/**
	 * 保存业务通知单，还要尝试自动分单
	 */
	public void save(Noticebill model) {
		User user = BosUtils.getLoginUser();
		model.setUser(user);//设置当前登陆用户
		noticebillDao.save(model);//保存业务通知单
		//获取客户取件地址
		String pickaddress = model.getPickaddress();
		//调用crm服务，根据客户地址查询定区ID
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if (decidedzoneId != null) {
			//查询到了定区ID，可以完成自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//业务关联到取派员
			//设置分单类型，自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//为取派员产生一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//创建系统时间
			workbill.setNoticebill(model);//工单关联页面通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//关联取派员对象
			workbill.setType(Workbill.TYPE_1);//工单类型
			workbillDao.save(workbill);//保存工单
			//调用短信平台给取派员发短信
			//把工单发送过去
		}else {
			//客户的地址不在服务范围内，需要建立额外服务
			//未查询到定区ID，不能完成自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
		
	
	
	}

}
