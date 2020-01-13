package com.itzpx.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itzpx.bos.domain.User;
import com.itzpx.bos.service.IUserService;
import com.itzpx.bos.utils.BosUtils;
import com.itzpx.bos.utils.MD5Utils;
import com.itzpx.bos.web.action.base.BaseAction;
import com.itzpx.crm.Customer;
import com.itzpx.crm.ICustomerService;

import freemarker.template.utility.SecurityUtilities;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	// ����ע��checkcodeҳ�������checkcode
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	private IUserService userService;

	/**
	 * �û���½��ʹ��shiro����ṩ�ķ�ʽ������֤
	 */
	public String login() {
		// ��session�л�ȡcheckcode
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNoneBlank(checkcode) && checkcode.equals(validatecode)) {
			//ʹ��shiro����ṩ�ķ�ʽ������֤����
			Subject subject = SecurityUtils.getSubject();//��õ�ǰ�û�����״̬Ϊ��Ϊ��֤��
			//AuthenticationToken���Ʒ�װ�û�������
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),
													MD5Utils.md5(model.getPassword()));
			try {
				subject.login(token);
			}catch (Exception e) {
				e.printStackTrace();
//				this.addActionError("�˺Ų����ڣ�");
				return LOGIN;
			}
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return HOME;
		} else {
			this.addActionError("�������֤�����");
			return LOGIN;
		}

	}
	/**
	 * �����û���½
	 */
	public String loginCp() {
		// ��session�л�ȡcheckcode
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNoneBlank(checkcode) && checkcode.equals(validatecode)) {
			User user = userService.login(model);
			if (user != null) {
				// ��½�ɹ�,��user�������session�У���תҳ��
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return HOME;
			} else {
				// ��֤ʧ�ܣ�
				this.addActionError("�û������������������");
				return LOGIN;
			}
		} else {
			this.addActionError("�������֤�����");
			return LOGIN;
		}

	}
	// �û�ע��
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	//�޸�����
	public String editPassword() throws IOException {
		String f = "1";
		//��ȡ��ǰ�û�
		User user = BosUtils.getLoginUser();
		//�޸�����ķ���
		try {
			userService.editPassword(user.getId(),model.getPassword());
		}catch (Exception e) {
			f = "0";
			// TODO: handle exception
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	/**
	 * ��ȡ��ɫid����
	 */
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	/**
	 * ����û�
	 * @return
	 */
	public String add() {
		userService.save(model,roleIds);
		return LIST;
	}
	/**
	 * ��ҳ��ѯ
	 * @return
	 */
	public String pageQuery() {
		userService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"noticebills","roles"});
		return NONE;
	}
}
