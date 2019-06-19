package cn.rejiejay.viewobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;

import com.alibaba.fastjson.JSONObject;

/**
 * 响应实体基础类型(默认为成功)
 * 
 * @param {string} message 返回失败的信息封装
 * @param {int}    result 返回失败的数据封装
 * @param {object} data 返回失败的数据封装
 * @author rejeijay
 * @Date 2019年6月19日11:01:01
 */
public class BaseReply extends JSONObject {
	/**
	 * 返回的结果, 用于前端进行判断。 成功: 1, 失败: !1
	 */
	@NotNull(message = "result cannot be null!")
	@DecimalMin(value = "0", message = "result must be more than 0!")
	private int result = 1;

	public int getResult() {
		return result;
	}

	public void Result(int result) {
		this.result = result;
	}

	/**
	 * 返回的错误信息, 用于前端进行提示。 默认是成功
	 */
	private String message = "success";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 返回的JSON对象， 默认是 {}
	 */
	private JSONObject data = new JSONObject();

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
}
