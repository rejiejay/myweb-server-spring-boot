package cn.rejiejay.viewobject;

/**
 * login响应实体类
 * 
 * @author rejeijay
 * @Date 2019年6月19日11:01:01
 */
public class LoginReply {

	private int id;
	private String username;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
