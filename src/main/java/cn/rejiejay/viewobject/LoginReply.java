package cn.rejiejay.viewobject;

import com.alibaba.fastjson.JSONObject;

/**
 * login响应实体类
 * 
 * @author _rejeijay
 * @Date 2019年6月19日11:01:01
 */
public class LoginReply {
	private String userToken = "not initiative in databases";
	
	public LoginReply() {
	}

	public LoginReply(String userToken) {
		this.userToken = userToken;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("token", userToken);
		
		return obj;
	}
}
