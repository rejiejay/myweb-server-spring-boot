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
import cn.rejiejay.viewobject.AndroidAddRecordReque;
import cn.rejiejay.viewobject.AndroidDelRecordReque;
import cn.rejiejay.viewobject.AndroidEditRecordReque;

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
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "type", required = false) String type) {
		Consequencer consequent = new Consequencer();

		logger.debug("@RequestMapping /recordevent/list?pageNo=" + pageNo + "&sort=" + sort+ "&type=" + type);

		int page = 1; // 默认返回第一页

		/**
		 *  总记录
		 */
		long allListCount = androidServer.listRecordEventCount(); // 不存在失败的说法
		
		/**
		 * type类别
		 */
		String dataType; // 数据类别默认 all
		if (type != null && type.equals("record")) {
			dataType = "record";
		} else if (type != null && type.equals("event")) {
			dataType = "event";
		} else {
			dataType = "all";
		}

		/**
		 * 随机排序
		 */
		Consequencer recordEventListResult;
		if (sort != null && sort.equals("random")) {
			recordEventListResult = androidServer.getRecordEventListByRandom(dataType, 10);
		} else {
			/**
			 * 时间排序
			 */
			if (pageNo != null && pageNo > 0) {
				page = pageNo.intValue();
			}
			recordEventListResult = androidServer.getRecordEventListByTime(dataType, page);
		}

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
	public JSONObject addRecord(@RequestBody @Valid AndroidAddRecordReque req, BindingResult result) {
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
	public JSONObject DelRecordEvent(@RequestBody @Valid AndroidDelRecordReque req, BindingResult result) {
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
		// String imagekey =
		// getOneRecordEventResult.getData().getString("imageidentity");

		/**
		 * 删除数据
		 */
		Consequencer delOneNoteResult = androidServer.delRecordEventBy(androidid);

		logger.debug("/android/recordevent/del[reply]: " + delOneNoteResult.getJsonStringMessage()); // 打印 请求参数
		return delOneNoteResult.getJsonObjMessage();
	}

	/**
	 * 编辑一条记录
	 */
	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/record/edit", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject editRecord(@RequestBody @Valid AndroidEditRecordReque req, BindingResult result) {
		logger.debug("/android/recordevent/del[req]: " + JSON.toJSONString(req)); // 打印 请求参数
		Consequencer consequent = new Consequencer();

		if (result.hasErrors()) { // 判断参数是否合法
			for (ObjectError error : result.getAllErrors()) {
				String errorMsg = error.getDefaultMessage();
				logger.warn("/java/notes/add[req]: " + errorMsg);
				return errorJsonReply(2, errorMsg);
			}
		}

		/**
		 * 先查询是否有这条数据
		 */
		int androidid = req.getAndroidid();
		// 获取一条id
		Consequencer getOneRecordEventResult = androidServer.getRecordEventBy(androidid);

		if (getOneRecordEventResult.getResult() != 1) {
			// 查询失败的情况下直接返回错误
			return consequent.setMessage("没有这条数据").getJsonObjMessage();
		}

		/**
		 * 处理图片(暂不实现 注意删除原来的图片 JAVAnotes 那边也忘记处理删除图片的了 这边测试完记得改一下JAVAnotes那边的
		 */
		// String imagekey =
		// getOneRecordEventResult.getData().getString("imageidentity");

		/**
		 * 编辑到数据库
		 */
		Consequencer editRecordResult = androidServer.editRecord(req);

		return editRecordResult.getJsonObjMessage();
	}
}
