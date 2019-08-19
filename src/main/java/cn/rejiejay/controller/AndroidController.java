package cn.rejiejay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.service.AndroidServer;
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

	@Autowired
	private AndroidServer androidServer;

	/**
	 * 获取安卓记录列表 排序分页暂时不做
	 * 
	 * @param {pageNo} 分页
	 * @param {sort}   排序方式 time random 返回total 和 list
	 */
	@RequestMapping(value = "/recordevent/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject listRecordEvent(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "sort", required = false) String sort) {
		Consequencer consequent = new Consequencer();
		
		logger.debug("@RequestMapping /recordevent/list?pageNo=" + pageNo + "&sort=" + sort);

		int page = 1; // 默认返回第一页

		/**
		 * 总记录 listTotal 是 必须 但是现阶段不用管
		 */
		long allListCount = androidServer.listRecordEventCount(); // 不存在失败的说法

		/**
		 * 先写 按照时间排序 其他的排序慢慢再加进去(乱序 限定时间 限定 记录 时间 时间范围 标签
		 */
		Consequencer recordEventListResult = androidServer.getRecordEventListByTime(page);
		
		// 执行失败的情况下直接返回失败即可
		if (recordEventListResult.getResult() != 1) {
			return recordEventListResult.getJsonObjMessage();
		}

		// 成功的情况下 返回 {list: [], total: ?}
		JSONObject data = new JSONObject();
		data.put("list", recordEventListResult.getData().getJSONArray("list"));
		data.put("total", allListCount);

		return consequent.setSuccess(data).getJsonObjMessage();
	}

}
