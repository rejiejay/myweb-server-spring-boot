package cn.rejiejay.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.security.MyHttpServletRequest;
import cn.rejiejay.utils.Consequencer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件上传
 * 
 * @author _rejeijay
 * @Date 2019年7月16日10:18:40
 */
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${rejiejay.file.upload-dir}")
	private String filePath;

	/**
	 * _通过text上传base64文件
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "text/plain;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public JSONObject uploadfile(@RequestBody String body) {
		Consequencer consequent = new Consequencer();

		/**
		 * 因为这里不使用拦截器，所以需要手动校验权限（可以简单实现实现
		 */
		
		

		if (body.length() <= 0) {
			return consequent.setMessage("上传失败，未上传任何文件，请选择文件。").getJsonObjMessage();
		}

		logger.debug("成功接收上传文件");
		String fileName = String.valueOf(new Date().getTime()); // 文件名称自己定义（等效于id

		File file = new File(filePath + fileName);
		PrintStream myPrintStream = null;
		try {
			myPrintStream = new PrintStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			String msg = "上传成功，但是写入失败" + e.toString();
			logger.warn(msg);
			return consequent.setMessage(msg).getJsonObjMessage();
		}
		
		myPrintStream.println(body); // 往文件里写入字符串
		myPrintStream.close();

		JSONObject data = new JSONObject();
		data.put("fileId", fileName); // 文件名称也就是唯一标识
		return consequent.setSuccess().setData(data).getJsonObjMessage();
	}
}
