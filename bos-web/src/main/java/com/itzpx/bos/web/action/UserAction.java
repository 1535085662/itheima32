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
	// 属性注入checkcode页面输入的checkcode
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	private IUserService userService;

	/**
	 * 用户登陆，使用shiro框架提供的方式进行认证
	 */
	public String login() {
		// 从session中获取checkcode
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNoneBlank(checkcode) && checkcode.equals(validatecode)) {
			//使用shiro框架提供的方式进行认证操作
			Subject subject = SecurityUtils.getSubject();//获得当前用户对象，状态为“为认证”
			//AuthenticationToken令牌封装用户名密码
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),
													MD5Utils.md5(model.getPassword()));
			try {
				subject.login(token);
			}catch (Exception e) {
				e.printStackTrace();
//				this.addActionError("账号不存在！");
				return LOGIN;
			}
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return HOME;
		} else {
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}

	}
	/**
	 * 复制用户登陆
	 */
	public String loginCp() {
		// 从session中获取checkcode
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if (StringUtils.isNoneBlank(checkcode) && checkcode.equals(validatecode)) {
			User user = userService.login(model);
			if (user != null) {
				// 登陆成功,将user对象放入session中，跳转页面
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return HOME;
			} else {
				// 验证失败，
				this.addActionError("用户名或者密码输入错误！");
				return LOGIN;
			}
		} else {
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}

	}
	// 用户注销
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	//修改密码
	public String editPassword() throws IOException {
		String f = "1";
		//获取当前用户
		User user = BosUtils.getLoginUser();
		//修改密码的方法
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
	 * 获取角色id集合
	 */
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	/**
	 * 添加用户
	 * @return
	 */
	public String add() {
		userService.save(model,roleIds);
		return LIST;
	}
	/**
	 * 分页查询
	 * @return
	 */
	public String pageQuery() {
		userService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"noticebills","roles"});
		return NONE;
	}
}
