package cn.rejiejay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.security.SecurityAnnotater;
import cn.rejiejay.service.JavaNotesServer;
import cn.rejiejay.service.OssServerImpl;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.viewobject.AddJavaNotesReque;

import java.util.Date;

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
	public JSONObject loginRejiejay(@RequestBody @Valid AddJavaNotesReque req, BindingResult result) {
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
}
