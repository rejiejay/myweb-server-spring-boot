package cn.rejiejay.viewobject;

import com.alibaba.fastjson.JSONObject;

/**
 * login响应实体类
 * 
 * @author _rejeijay
 * @Date 2019年6月19日11:01:01
 */
public class AuthorizeReply {
	private String userToken = "not initiative in databases";
	private Long tokenexpired;
	
	public AuthorizeReply() {
	}

	public AuthorizeReply(String userToken, Long tokenexpired) {
		this.userToken = userToken;
		this.tokenexpired = tokenexpired;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("token", this.userToken);
		obj.put("tokenexpired", this.tokenexpired);
		
		return obj;
	}
}
