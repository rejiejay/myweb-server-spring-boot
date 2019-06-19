package cn.rejiejay.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * JAVA组件通讯结果类
 * 
 * @author rejeijay
 * @Date 2019年6月19日22:09:17
 */
public class Consequencer {
	/**
	 * 基础的通讯结果类 （默认是成功
	 * 
	 * @return {JSONObject} result message
	 */
	static JSONObject baseJSONobj() {
		JSONObject baseObj = new JSONObject();
		baseObj.put("result", 1);
		baseObj.put("message", "successful");

		return baseObj;
	}
	
	/**
	 * 响应的成功格式 (JSON格式)
	 * 
	 * @param {JSONObject} data 返回的数据
	 * @param {string}     message 返回的错误信息, 用于前端进行提示。
	 */
	static JSONObject success(JSONObject data) {
		JSONObject succeedJson = baseJSONobj();
		succeedJson.put("data", data);

		return succeedJson;
	}

	static JSONObject success(JSONObject data, String message) {
		JSONObject succeedJson = baseJSONobj();
		succeedJson.put("data", data);
		succeedJson.put("message", message);

		return succeedJson;
	}
	
	/**
	 * 响应的失败格式 (JSON格式)
	 * 
	 * @param {JSONObject} data 返回的数据
	 * @param {string}     message 返回的错误信息, 用于前端进行提示。
	 */
	static JSONObject error(int result, String message) {
		// 因为响应的是错误信息, 所以不能传入的值为1
		if (result == 1) {
			result = 0;
		}

		JSONObject errorJson = baseJSONobj();
		errorJson.put("result", result);
		errorJson.put("message", message);

		return errorJson;
	}

	static JSONObject error(int result) {
		JSONObject errorJson = baseJSONobj();
		errorJson.put("message", "failure");

		// 因为响应的是错误信息, 所以不能传入的值为1
		if (result == 1) {
			result = 0;
		}

		errorJson.put("result", result);

		return errorJson;
	}
}
