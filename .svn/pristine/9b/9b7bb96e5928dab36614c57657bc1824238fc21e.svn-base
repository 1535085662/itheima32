package com.itzpx.bos.web.interceptor;

import com.itzpx.bos.domain.User;
import com.itzpx.bos.utils.BosUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 实现用户未登录页面跳转
 * @author 曾大爷
 *
 */
public class BosLoginInterceptor extends MethodFilterInterceptor{
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//获取登陆用户
		User user = BosUtils.getLoginUser();
		if (user == null) {
			return "login";
		}
		return invocation.invoke();
	}

}
