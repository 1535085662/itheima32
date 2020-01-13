package com.itzpx.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itzpx.bos.domain.User;

/**
 * 	Bos项目工具类
 * @author 曾大爷
 *
 */
public class BosUtils {
	//获取session对象
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	//获取登陆用户对象
	public static User getLoginUser() {
		return (User) getSession().getAttribute("loginUser");
	}
}
