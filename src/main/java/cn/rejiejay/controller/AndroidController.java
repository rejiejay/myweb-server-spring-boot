package cn.rejiejay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.security.SecurityAnnotater;
import cn.rejiejay.service.AndroidServer;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.viewobject.AddRecordReque;
import cn.rejiejay.viewobject.DelRecordReque;

import javax.validation.Valid;

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

	/**
	 * 新增记录
	 */
	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/record/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject addRecord(@RequestBody @Valid AddRecordReque req, BindingResult result) {
		logger.debug("/java/notes/add[req]: " + JSON.toJSONString(req)); // 打印 请求参数

		if (result.hasErrors()) { // 判断参数是否合法
			for (ObjectError error : result.getAllErrors()) {
				String errorMsg = error.getDefaultMessage();
				logger.warn("/android/record/add[req]: " + errorMsg);
				return errorJsonReply(2, errorMsg);
			}
		}

		/**
		 * 处理图片
		 */
		String imageId = req.getImageidentity();
		if (imageId != null && !imageId.equals("") && !imageId.equals("null")) { // 不为空的情况下
			// 暂不实现
			// Consequencer uploadResult =
			// androidServer.uploadJavaNotesImage(imageId);

			// 处理失败
			// if (uploadResult.getResult() != 1) {
			// return uploadResult.getJsonObjMessage();
			// }
		}
		
		return androidServer.addRecord(req).getJsonObjMessage();
	}
	
	/**
	 * 删除一条记录
	 */
	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/recordevent/del", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject DelRecordEvent(@RequestBody @Valid DelRecordReque req, BindingResult result) {
		logger.debug("/android/recordevent/del[req]: " + JSON.toJSONString(req)); // 打印 请求参数
		int androidid = req.getAndroidid();
		
		/**
		 * 手动参数校验
		 */
		if (androidid < 0) {
			return errorJsonReply(2, "请求参数错误, 唯一标识不能为空!");
		}
		
		// 获取一条id
		Consequencer getOneRecordEventResult = androidServer.getRecordEventBy(androidid);

		if (getOneRecordEventResult.getResult() != 1) {
			// 查询失败的情况下直接返回错误
			return getOneRecordEventResult.getJsonObjMessage();
		}

		/**
		 * 处理图片(暂不实现
		 */
		// String imagekey = getOneRecordEventResult.getData().getString("imageidentity");
		
		/**
		 * 删除数据
		 */
		Consequencer delOneNoteResult = androidServer.delRecordEventBy(androidid);

		logger.debug("/android/recordevent/del[reply]: " + delOneNoteResult.getJsonStringMessage()); // 打印 请求参数
		return delOneNoteResult.getJsonObjMessage();
	}

}
