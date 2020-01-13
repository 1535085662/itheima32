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
 * shiro���BOSRealm��
 * @author ����ү
 *
 */
public class BOSRealm extends AuthorizingRealm{

	
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	//��֤����
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//�����û�����ѯ���ݿ��е�����
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		//���ҳ����û���
		String username = passwordToken.getUsername();
		//�����û�����ѯ���ݿ��е�����
		User user = userDao.findUserByUsername(username);
		if (user == null) {
			//�û���������
			return null;
		}
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		//��ܸ���ȶ����ݿ��е������ҳ���Ƿ�һ��
		return info;
	}
	//��Ȩ����
		protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
			//Ϊ�û���Ȩ
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// ���ݵ�ǰ�û���ѯ���ݿ⣬��ȡʵ�ʶ�Ӧ��Ȩ��
			//��ȡ��ǰ�û�
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			//User user2 = (User) principals.getPrimaryPrincipal();
			List<Function> list = null;
			if(user.getUsername().equals("admin")) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
				//��������Ա�����û�����ȡȨ��
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
