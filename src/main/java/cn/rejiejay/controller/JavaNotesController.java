package cn.rejiejay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import cn.rejiejay.service.JavaNotesServer;
import cn.rejiejay.service.OssServerImpl;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.viewobject.AddJavaNotesReque;
import cn.rejiejay.viewobject.EditJavaNotesReque;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 笔记系统
 * 
 * @author _rejeijay
 * @Date 2019年7月16日10:18:40
 */
@RestController
@RequestMapping("/java/notes")
public class JavaNotesController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaNotesServer javaNotesServer;

	@Autowired
	private OssServerImpl ossService;

	/**
	 * 新增笔记
	 */
	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject addNotes(@RequestBody @Valid AddJavaNotesReque req, BindingResult result) {
		logger.debug("/java/notes/add[req]: " + JSON.toJSONString(req)); // 打印 请求参数

		if (result.hasErrors()) { // 判断参数是否合法
			for (ObjectError error : result.getAllErrors()) {
				String errorMsg = error.getDefaultMessage();
				logger.warn("/java/notes/add[req]: " + errorMsg);
				return errorJsonReply(2, errorMsg);
			}
		}

		/**
		 * 处理图片
		 */
		String imageId = req.getImageId();
		if (imageId != null && !imageId.equals("") && !imageId.equals("null")) { // 不为空的情况下
			Consequencer uploadResult = ossService.uploadJavaNotesImage(imageId);

			// 处理失败
			if (uploadResult.getResult() != 1) {
				return uploadResult.getJsonObjMessage();
			}
		}

		/**
		 * 保存到数据库
		 */
		String title = req.getTitle();
		String htmlContent = req.getHtmlContent();

		Consequencer consequent = javaNotesServer.uploadJavaNotes(title, imageId, htmlContent);

		return consequent.getJsonObjMessage();
	}

	/**
	 * 获取笔记列表，
	 * 
	 * @param {pageNo} 分页
	 * @param {sort}   排序方式 time random
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject listNotes(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "sort", required = false) String sort) {
		Consequencer consequent = new Consequencer();

		int page = 1;
		/**
		 * 总记录notesTotal 是 必须
		 */
		long allNotesCount = javaNotesServer.getAllNotesCount(); // 不存在失败的说法

		/**
		 * 默随机排序
		 */
		if (sort != null && sort.equals("random")) {
			Consequencer getNoteResult = javaNotesServer.getNotesByRandom(10);
			
			// 执行失败的情况下直接返回失败即可
			if (getNoteResult.getResult() != 1) {
				return getNoteResult.getJsonObjMessage();
			}

			// 成功的情况下 返回 {javaNotes: [], total: ?}
			JSONObject data = new JSONObject();
			data.put("javaNotes", getNoteResult.getData().getJSONArray("javaNotes"));
			data.put("total", allNotesCount);

			// 封装一下
			return consequent.setSuccess(data).getJsonObjMessage();
		}

		/**
		 * 默认时间排序，(只要不是随机排序就是时间排序
		 */
		// if (sort == null || sort.equals("time")) {}
		if (pageNo != null && pageNo > 0) {
			page = pageNo.intValue();
		}

		Consequencer getNoteResult = javaNotesServer.getNotesByTime(page);

		// 执行失败的情况下直接返回失败即可
		if (getNoteResult.getResult() != 1) {
			return getNoteResult.getJsonObjMessage();
		}

		// 成功的情况下 返回 {javaNotes: [], total: ?}
		JSONObject data = new JSONObject();
		data.put("javaNotes", getNoteResult.getData().getJSONArray("javaNotes"));
		data.put("total", allNotesCount);

		// 封装一下
		return consequent.setSuccess(data).getJsonObjMessage();
	}

	/**
	 * 随机获取一条笔记
	 */
	@RequestMapping(value = "/get/random", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public JSONObject getOneNotes() {
		Consequencer consequent = new Consequencer();

		Consequencer getNoteResult = javaNotesServer.getNotesByRandom(1);

		// 执行失败的情况下直接返回失败即可
		if (getNoteResult.getResult() != 1) {
			return getNoteResult.getJsonObjMessage();
		}

		// 成功的情况下 返回 {javaNotes: [], total: ?}
		JSONObject data = (JSONObject) getNoteResult.getData().getJSONArray("javaNotes").get(0);

		// 封装一下
		return consequent.setSuccess(data).getJsonObjMessage();
	}

	/**
	 * 根据ID删除一条笔记
	 */
	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/del", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject delNoteById(@RequestBody JSONObject req) {
		Integer id = req.getInteger("id");
		logger.debug("/java/notes/del[req]: id:" + id);
		
		/**
		 * 手动参数校验
		 */
		if (id == null || id < 0) {
			return errorJsonReply(2, "请求参数错误!");
		}
		
		/**
		 * 根据id查询这条数据
		 */
		Consequencer getOneNoteResult = javaNotesServer.getNoteById(Long.valueOf(id.longValue()));
		
		if (getOneNoteResult.getResult() != 1) {
			// 查询失败的情况下直接返回错误
			return getOneNoteResult.getJsonObjMessage();
		}
		
		/**
		 * 先根据id删除图片
		 */
		String imagekey = getOneNoteResult.getData().getString("imagekey");
		if (imagekey != null && !imagekey.equals("")) {
			Consequencer delOneNotesImageResult = ossService.delJavaNotesImage(imagekey);
			
			if (delOneNotesImageResult.getResult() != 1) {
				return delOneNotesImageResult.getJsonObjMessage();
			}
		}
		
		/**
		 * 再根据id删除数据
		 */
		Consequencer delOneNoteResult = javaNotesServer.delNoteById(Long.valueOf(id.longValue()));
		
		logger.debug("/java/notes/del[reply]: " + delOneNoteResult.getJsonStringMessage());
		return delOneNoteResult.getJsonObjMessage();
	}

	/**
	 * 编辑笔记
	 */
	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject editNotes(@RequestBody @Valid EditJavaNotesReque req, BindingResult result) {
		logger.debug("/java/notes/edit[req]: " + JSON.toJSONString(req)); // 打印 请求参数

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
		long id = req.getId();
		Consequencer getNoteResult = javaNotesServer.getNoteById(id);
		
		// 处理失败
		if (getNoteResult.getResult() != 1) {
			return getNoteResult.getJsonObjMessage();
		}

		/**
		 * 处理图片(需要判断进行对比图片，避免重复上传
		 */
		String imageId = req.getImageId();
		if (imageId != null && !imageId.equals("") && !imageId.equals("null")) { // 不为空的情况下
			/**
			 * 判断OSS是否存在图片
			 */
			Consequencer javaNotesImageExists = ossService.isExistsJavaNotesImage(imageId);

			// 不存在这张图片
			if (javaNotesImageExists.getResult() != 1) {
				// 说明需要上传
				Consequencer uploadResult = ossService.uploadJavaNotesImage(imageId);

				// 处理失败
				if (uploadResult.getResult() != 1) {
					return uploadResult.getJsonObjMessage();
				}
			}
		}
		
		/**
		 * 编辑到数据库
		 */
		String title = req.getTitle();
		String htmlContent = req.getHtmlContent();
		
		Consequencer delOneNoteResult = javaNotesServer.editJavaNotes(id, title, imageId, htmlContent);

		return delOneNoteResult.getJsonObjMessage();
	}
}
