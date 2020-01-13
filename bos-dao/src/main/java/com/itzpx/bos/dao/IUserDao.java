package com.itzpx.bos.dao;

import com.itzpx.bos.dao.base.IBaseDao;
import com.itzpx.bos.domain.User;
/**
 * 	用户User接口，继承IBaseDao接口后，需要扩展方法就写这里
 * @author 曾大爷
 *
 */
public interface IUserDao extends IBaseDao<User>{

	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);

}
