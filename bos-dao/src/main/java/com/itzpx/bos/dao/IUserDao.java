package com.itzpx.bos.dao;

import com.itzpx.bos.dao.base.IBaseDao;
import com.itzpx.bos.domain.User;
/**
 * 	�û�User�ӿڣ��̳�IBaseDao�ӿں���Ҫ��չ������д����
 * @author ����ү
 *
 */
public interface IUserDao extends IBaseDao<User>{

	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);

}
