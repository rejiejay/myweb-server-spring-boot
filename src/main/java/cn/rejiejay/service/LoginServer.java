package cn.rejiejay.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rejiejay.dataaccessobject.User;
import cn.rejiejay.dataaccessobject.UserRepository;

/**
 * 登录逻辑模块
 * 
 * @author _rejeijay
 * @Date 2019年6月10日22:07:04
 */
public class LoginServer  {
	
	@Autowired
	private UserRepository userRepository;

	public LoginServer() {
		Iterable<User> aaa = userRepository.findAll();
	}
}
