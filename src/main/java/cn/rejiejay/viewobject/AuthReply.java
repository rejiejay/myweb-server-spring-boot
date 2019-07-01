package cn.rejiejay.viewobject;

import com.alibaba.fastjson.JSONObject;

/**
 * 认证 响应实体类
 * 
 * @author _rejeijay
 * @Date 2019年7月1日13:37:19
 */
public class AuthReply {
	private String AuthToken = "not initiative in databases";
	
	public AuthReply() {
	}

	public AuthReply(String AuthToken) {
		this.AuthToken = AuthToken;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("token", this.AuthToken);
		
		return obj;
	}
}
