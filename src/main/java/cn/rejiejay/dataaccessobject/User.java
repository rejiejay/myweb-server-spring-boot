package cn.rejiejay.dataaccessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 用户模块 实体类
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	@NotNull
	private Long userid;

	/**
	 * 现阶段只有一个是 _rejiejay
	 */
	@Column(name = "username")
	@NotNull
    private String username;
	
	/**
	 * 密码是一周刷新一次
	 */
	@Column(name = "password")
	@NotNull
    private String password;
	
	/**
	 * 现阶段只有 _admin 和 anonymous 也就是游客
	 */
	@Column(name = "role", nullable = true)
    private String role;
	
	/**
	 * 登录使用的凭证
	 * 7200秒刷新一次即可
	 * 如何刷新可参考微信文档
	 * 携带用户姓名，一般用于权限过滤
	 */
	@Column(name = "token")
    private String token;
	
	/**
	 * 签名
	 * 通过密匙生成的一串 数字签名
	 * 一般携带过期时间
	 * 一般用于对比，7200秒刷新一次即可
	 */
	@Column(name = "signature")
    private String signature;
	
	/**
	 * 私匙
	 * 一般是对外API对接的时候使用到的
	 * 这是在确认用户的情况下使用到的、
	 * 需要加密解密的时候用到的，一般不记录在前端、并且密匙一般不刷新、
	 */
	@Column(name = "secret")
    private String secret;
	
	@Column(name = "tokenexpired")
    private long tokenexpired;
	
	@Column(name = "signexpired")
    private long signexpired;
	
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public long getTokenexpired() {
		return tokenexpired;
	}

	public void setTokenexpired(long tokenexpired) {
		this.tokenexpired = tokenexpired;
	}

	public long getSignexpired() {
		return signexpired;
	}

	public void setSignexpired(long signexpired) {
		this.signexpired = signexpired;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}



