package cn.rejiejay.viewobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 认证 请求实体类
 * 
 * @author _rejeijay
 * @Date 2019年7月1日13:37:23
 */
public class AuthReque {
	@NotNull(message = "username cannot be null!")
	private String username;
	
	@NotNull(message = "password cannot be null!")
	@Size(min = 6, max = 16, message = "password is wrong!")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
