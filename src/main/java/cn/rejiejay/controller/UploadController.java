package cn.rejiejay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.utils.Consequencer;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
	
	/**
	 * 通过表单上传文件
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json;charset=UTF-8")
	public JSONObject upload(@RequestParam("file") MultipartFile file) {
		Consequencer consequent = new Consequencer();
		
		/**
		 * 因为这里不使用拦截器，所以需要手动校验权限（可以简单实现实现
		 */
		
        if (file.isEmpty()) {
            return consequent.setMessage("上传失败，未上传任何文件，请选择文件。").getJsonObjMessage();
        }
        
        logger.debug("成功接收上传文件:" + file.getOriginalFilename());
        String fileName = String.valueOf(new Date().getTime()); // 文件名称自己定义（等效于id
        
        String filePath = "/"; // 以'/'开头是绝对路径的path，指向你程序的根目录
        
        File dest = new File(filePath + fileName);
        

        try {
			file.transferTo(dest);
		} catch (IllegalStateException | IOException e) {
			String errorMsg = "文件上传失败，原因：" + e.toString();
			logger.error(errorMsg);
            return consequent.setMessage(errorMsg).getJsonObjMessage();
		}
        
		return consequent.setSuccess().getJsonObjMessage();
	}
}
