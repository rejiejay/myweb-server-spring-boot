package cn.rejiejay.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

/**
 * JAVA组件通讯结果类 (默认成功
 * 
 * @param {string} message 返回失败的信息封装
 * @param {int}    result 返回失败的数据封装
 * @param {object} data 返回失败的数据封装
 * @author _rejeijay
 * @Date 2019年6月19日22:09:17
 */
public class Consequencer {
	/**
	 * 返回的结果, 用于判断。 成功: 1, 失败: !1 默认失败
	 */
	private int result = 0;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * 返回的错误信息, 用于提示信息。 默认是失败
	 */
	private String message = "failure";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 返回的对象， 用于存储额外信息。 默认是 空对象
	 */
	private JSONObject data = new JSONObject();

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	/**
	 * 返回JSON信息
	 */
	public JSONObject getJsonObjMessage() {
		JSONObject jsonObjMessage = new JSONObject();
		jsonObjMessage.put("result", result);
		jsonObjMessage.put("message", message);
		jsonObjMessage.put("data", data);

		return jsonObjMessage;
	}

	/**
	 * 返回字符串信息
	 */
	public String getJsonStringMessage() {
		JSONObject jsonObjMessage = new JSONObject();
		jsonObjMessage.put("result", result);
		jsonObjMessage.put("message", message);
		jsonObjMessage.put("data", data);

		return JSON.toJSONString(jsonObjMessage);
	}
}
