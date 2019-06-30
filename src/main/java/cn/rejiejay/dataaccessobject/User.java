package cn.rejiejay.dataaccessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户模块 实体类
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 这个是设为主键的意思
	@Column(name = "user_id")
	private Long userid;
	
	@Column(name = "username")
    private String username;

	@Column(name = "password")
    private String password;
	
	@Column(name = "token")
    private String token;
	
	@Column(name = "role")
    private String role;
	
	// token 为一星期过期
	@Column(name = "token_expired")
    private String tokenexpired;

    public User() {}
	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTokenexpired() {
		return tokenexpired;
	}

	public void setTokenexpired(String tokenexpired) {
		this.tokenexpired = tokenexpired;
	}
}



