package com.itzpx.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.itzpx.bos.dao.IFunctionDao;
import com.itzpx.bos.dao.IUserDao;
import com.itzpx.bos.domain.Function;
import com.itzpx.bos.domain.User;
/**
 * shiro框架BOSRealm类
 * @author 曾大爷
 *
 */
public class BOSRealm extends AuthorizingRealm{

	
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//根据用户名查询数据库中的密码
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		//获得页面的用户名
		String username = passwordToken.getUsername();
		//根据用户名查询数据库中的密码
		User user = userDao.findUserByUsername(username);
		if (user == null) {
			//用户名不存在
			return null;
		}
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		//框架负责比对数据库中的密码和页面是否一致
		return info;
	}
	//授权方法
		protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
			//为用户授权
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 根据当前用户查询数据库，获取实际对应的权限
			//获取当前用户
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			//User user2 = (User) principals.getPrimaryPrincipal();
			List<Function> list = null;
			if(user.getUsername().equals("admin")) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
				//超级管理员内置用户，获取权限
				list = functionDao.finByCriteria(detachedCriteria);
			}else {
				
				list = functionDao.findFunctionListByUserId(user.getId());
			}
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
			return info;
		}

}
