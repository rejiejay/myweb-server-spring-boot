package cn.rejiejay.controller;

import com.alibaba.fastjson.JSONObject;
import cn.rejiejay.utils.Consequencer;;

/**
 * 基础类
 * 
 * @author _rejeijay
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

		Consequencer consequence = new Consequencer();
		consequence.setResult(result);
		consequence.setMessage(message);

		return consequence.getJsonObjMessage();
	}

	protected JSONObject errorJsonReply(int result) {
		Consequencer consequence = new Consequencer();

		consequence.setMessage("error");

		// 因为响应的是错误信息, 所以不能传入的值为1
		if (result == 1) {
			result = 0;
		}

		consequence.setResult(result);

		return consequence.getJsonObjMessage();
	}

	/**
	 * 响应的成功格式 (JSON格式)
	 * 
	 * @param {JSONObject} data 返回的数据
	 * @param {string}     message 返回的错误信息, 用于前端进行提示。
	 */
	protected JSONObject succeedJsonReply(JSONObject data, String message) {
		Consequencer consequence = new Consequencer();
		consequence.setResult(1);
		consequence.setData(data);
		consequence.setMessage(message);

		return consequence.getJsonObjMessage();
	}

	protected JSONObject succeedJsonReply(JSONObject data) {
		Consequencer consequence = new Consequencer();
		consequence.setResult(1);
		consequence.setMessage("succeed");
		consequence.setData(data);

		return consequence.getJsonObjMessage();
	}
}
