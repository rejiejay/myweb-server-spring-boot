package cn.rejiejay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.utils.Consequencer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 安卓端支持（部分支持web端
 * 
 * @author _rejeijay
 * @Date 2019年7月16日10:18:40
 */
@RestController
@RequestMapping("/android")
public class AndroidController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取安卓记录列表
	 * 排序分页暂时不做
	 * 
	 * @param {pageNo} 分页
	 * @param {sort}   排序方式 time random
	 * 返回total 和 list
	 */
	@RequestMapping(value = "/recordevent/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject listRecordEvent(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "sort", required = false) String sort) {
		Consequencer consequent = new Consequencer();
		
		int page = 1; // 默认返回第一页

		/**
		 * 总记录 listTotal 是 必须 但是现阶段不用管
		 */
		// long allListCount = javaNotesServer.getAllNotesCount(); // 不存在失败的说法
		
		
		
		return consequent.getJsonObjMessage();
	}

}
