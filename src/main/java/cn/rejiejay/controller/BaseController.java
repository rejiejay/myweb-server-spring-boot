package cn.rejiejay.controller;

import com.alibaba.fastjson.JSONObject;

/**
 * 基础类
 * 
 * @author rejeijay
 * @Date 2019年6月10日10:26:14
 */
public class BaseController {

	/**
	 * 响应的失败格式 (JSON格式)
	 * 
	 * @param {int}    result 返回的结果, 用于前端进行判断
	 * @param {string} message 返回的错误信息, 用于前端进行提示。
	 */
	protected JSONObject errorJsonReply(int result, String message) {
		// 因为响应的是错误信息, 所以不能传入的值为1
		if (result == 1) {
			result = 0;
		}

		JSONObject errorJson = new JSONObject();
		errorJson.put("result", result);
		errorJson.put("message", message);

		return errorJson;
	}

	protected JSONObject errorJsonReply(int result) {
		JSONObject errorJson = new JSONObject();
		errorJson.put("message", "error");

		// 因为响应的是错误信息, 所以不能传入的值为1
		if (result == 1) {
			result = 0;
		}

		errorJson.put("result", result);

		return errorJson;
	}

	/**
	 * 响应的成功格式 (JSON格式)
	 * 
	 * @param {JSONObject} data 返回的数据
	 * @param {string}     message 返回的错误信息, 用于前端进行提示。
	 */
	protected JSONObject succeedJsonReply(JSONObject data, String message) {
		JSONObject succeedJson = new JSONObject();
		succeedJson.put("result", 1);
		succeedJson.put("result", data);
		succeedJson.put("message", message);

		return succeedJson;
	}

	protected JSONObject succeedJsonReply(JSONObject data) {
		JSONObject succeedJson = new JSONObject();
		succeedJson.put("result", 1);
		succeedJson.put("result", data);
		succeedJson.put("message", "successful");

		return succeedJson;
	}
}
