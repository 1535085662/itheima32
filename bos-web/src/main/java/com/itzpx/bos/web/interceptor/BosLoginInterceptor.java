package com.itzpx.bos.web.interceptor;

import com.itzpx.bos.domain.User;
import com.itzpx.bos.utils.BosUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * ʵ���û�δ��¼ҳ����ת
 * @author ����ү
 *
 */
public class BosLoginInterceptor extends MethodFilterInterceptor{
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//��ȡ��½�û�
		User user = BosUtils.getLoginUser();
		if (user == null) {
			return "login";
		}
		return invocation.invoke();
	}

}
