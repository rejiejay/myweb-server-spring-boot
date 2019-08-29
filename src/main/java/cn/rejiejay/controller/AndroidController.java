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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.security.SecurityAnnotater;
import cn.rejiejay.service.AndroidServer;
import cn.rejiejay.service.AndroidServerStatistics;
import cn.rejiejay.service.OssServerImpl;
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

	@Autowired
	private AndroidServerStatistics androidServerStatistics;

	@Autowired
	private OssServerImpl ossService;

	/**
	 * 获取安卓记录列表
	 * 
	 * @param {pageNo} 分页
	 * @param {sort}   排序方式 time random 返回total 和 list
	 */
	@RequestMapping(value = "/recordevent/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject listRecordEvent(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "tag", required = false) String tag) {
		Consequencer consequent = new Consequencer();

		logger.debug("@RequestMapping /recordevent/list?pageNo=" + pageNo + "&sort=" + sort + "&type=" + type);

		int page = 1; // 默认返回第一页

		/**
		 * 总记录
		 */
		long allListCount = androidServer.listRecordEventCount(); // 不存在失败的说法

		/**
		 * tag类别
		 */
		String dataTag = "all"; // 数据类别默认 all
		if (tag != null && tag.length() > 0) {
			dataTag = tag;
		}

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
			recordEventListResult = androidServer.getRecordEventListByRandom(dataType, dataTag, 10);
		} else {
			/**
			 * 时间排序
			 */
			if (pageNo != null && pageNo > 0) {
				page = pageNo.intValue();
			}
			recordEventListResult = androidServer.getRecordEventListByTime(dataType, dataTag, page);
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
	 * 根据时间范围 获取安卓记录列表
	 * 
	 * @param {pageNo}       分页
	 * @param {minTimestamp}
	 * @param {maxTimestamp}
	 */
	@RequestMapping(value = "/recordevent/getbytime", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject listRecordEventByTime(@RequestParam(value = "pageNo", required = true) Integer pageNo,
			@RequestParam(value = "minTimestamp", required = true) long minTimestamp,
			@RequestParam(value = "maxTimestamp", required = true) long maxTimestamp) {
		logger.debug(
				"根据时间范围 获取安卓记录列表 pageNo:" + pageNo + "、minTimestamp:" + minTimestamp + "、maxTimestamp:" + maxTimestamp);
		Consequencer consequent = new Consequencer();
		int pageInt = pageNo.intValue();

		/**
		 * 统计时间段的总记录
		 */
		long allListCount = androidServer.countRecordEventTimestamp(minTimestamp, maxTimestamp);

		/**
		 * 获取 列表 根据时间戳范围
		 */
		Consequencer recordEventListResult = androidServer.listRecordEventByTime(minTimestamp, maxTimestamp, pageInt);

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
		if (imageId != null && imageId.length() > 0) { // 不为空的情况下
			Consequencer uploadResult = ossService.uploadAndroidImage(imageId);

			// 处理失败
			if (uploadResult.getResult() != 1) {
				return uploadResult.getJsonObjMessage();
			}
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
		 * 处理图片
		 */
		String imagekey = getOneRecordEventResult.getData().getString("imageidentity");
		if (imagekey != null && imagekey.length() > 0) {
			Consequencer delImageResult = ossService.delAndroidImage(imagekey);
			if (delImageResult.getResult() != 1) {
				return delImageResult.getJsonObjMessage();
			}
		}

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
		 * 处理图片
		 */
		String imageId = getOneRecordEventResult.getData().getString("imageidentity");
		String imagekey = req.getImageidentity();
		/**
		 * 先判断原来是否有图片
		 * 有图片的情况下，判断是不是和原先的图片是同一个ID
		 */
		if (imageId != null && imageId.length() > 0 && !imageId.equals(imagekey)) {
			// 原来有图片，并且和新上传的图片Id不一样！（包括不上传新图片也是不一样）执行删除
			Consequencer delImageResult = ossService.delAndroidImage(imageId);
			if (delImageResult.getResult() != 1) {
				return delImageResult.getJsonObjMessage();
			}
		}
		// 再判断有没有上传新的图片
		if (imagekey != null && imagekey.length() > 0) {
			Consequencer uploadResult = ossService.uploadAndroidImage(imagekey);
			if (uploadResult.getResult() != 1) {
				return uploadResult.getJsonObjMessage();
			}
		}

		/**
		 * 编辑到数据库
		 */
		Consequencer editRecordResult = androidServer.editRecord(req);

		return editRecordResult.getJsonObjMessage();
	}

	/**
	 * 获取 标签
	 */
	@RequestMapping(value = "/recordevent/tag/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject getRecordEventTag() {
		return androidServer.getRecordEventTag().getJsonObjMessage();
	}

	/**
	 * 新增 标签
	 */
	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/recordevent/tag/add", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject addRecordEventTag(@RequestParam(value = "tag", required = true) String tag) {
		logger.debug("/recordevent/tag/add[req]: " + tag); // 打印 请求参数

		if (tag == null || tag.length() <= 0) {
			return errorJsonReply(2, "参数不能为空");
		}

		return androidServer.addRecordEventTag(tag).getJsonObjMessage();
	}

	/**
	 * 删除 标签
	 */
	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/recordevent/tag/del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject delRecordEventTag(@RequestParam(value = "id", required = true) int id) {
		logger.debug("/recordevent/tag/add[req]: " + id); // 打印 请求参数

		if (id < 1) {
			return errorJsonReply(2, "参数不能小于1");
		}

		return androidServer.delRecordEventTag(id).getJsonObjMessage();
	}

	/**
	 * 获取 日期统计数据
	 */
	@RequestMapping(value = "/recordevent/date/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject getRecordEventDate() {
		JSONObject data = new JSONObject();

		Consequencer StatisticResult = androidServerStatistics.downloadStatistic();
		JSONArray statisticArray = StatisticResult.getData().getJSONArray("statistic");
		JSONArray statisticDeWeig = androidServerStatistics.statisticDeWeighting(statisticArray);

		data.put("statistic", statisticDeWeig);

		return new Consequencer().setSuccess(data).getJsonObjMessage();
	}
}
