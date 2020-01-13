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
	 * ����ҵ��֪ͨ������Ҫ�����Զ��ֵ�
	 */
	public void save(Noticebill model) {
		User user = BosUtils.getLoginUser();
		model.setUser(user);//���õ�ǰ��½�û�
		noticebillDao.save(model);//����ҵ��֪ͨ��
		//��ȡ�ͻ�ȡ����ַ
		String pickaddress = model.getPickaddress();
		//����crm���񣬸��ݿͻ���ַ��ѯ����ID
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if (decidedzoneId != null) {
			//��ѯ���˶���ID����������Զ��ֵ�
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//ҵ�������ȡ��Ա
			//���÷ֵ����ͣ��Զ��ֵ�
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//Ϊȡ��Ա����һ������
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//׷������
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//����ϵͳʱ��
			workbill.setNoticebill(model);//��������ҳ��֪ͨ��
			workbill.setPickstate(Workbill.PICKSTATE_NO);//ȡ��״̬
			workbill.setRemark(model.getRemark());//��ע��Ϣ
			workbill.setStaff(staff);//����ȡ��Ա����
			workbill.setType(Workbill.TYPE_1);//��������
			workbillDao.save(workbill);//���湤��
			//���ö���ƽ̨��ȡ��Ա������
			//�ѹ������͹�ȥ
		}else {
			//�ͻ��ĵ�ַ���ڷ���Χ�ڣ���Ҫ�����������
			//δ��ѯ������ID����������Զ��ֵ�
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
		
	
	
	}

}
