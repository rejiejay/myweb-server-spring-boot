package cn.rejiejay.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.dataaccessobject.JavaNotesRepository;
import cn.rejiejay.utils.Consequencer;

/**
 * 腾讯云对象存储 模块 实现类
 * 
 * @author _rejeijay
 * @Date 2019-7-16 16:11:38
 */
@Service
public class JavaNotesServerImpl implements JavaNotesServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaNotesRepository javaNotesRepository;

	@Value("${rejiejay.tencentoss.origin}")
	private String tencentOssOrigin;

	/**
	 * 创建一个笔记
	 */
	public Consequencer uploadJavaNotes(String title, String imageId, String htmlContent) {
		Consequencer consequent = new Consequencer();

		long timestamp = new Date().getTime();

		logger.debug("执行SQL" + "title:" + title + ";htmlContent:" + htmlContent
				+ ";imageId:" + imageId + ";timestamp:" + timestamp);
		
		int insertNoteResult = javaNotesRepository.insertNote(title, htmlContent, imageId, timestamp);
		

		if (insertNoteResult == 1) {
			JSONObject data = new JSONObject();
			data.put("title", title);
			data.put("htmlContent", htmlContent);
			data.put("imageId", imageId);

			/**
			 * 返回 上传OSS的地址
			 */
			String imageUrl = null;
			if (imageId != null && !imageId.equals("") && !imageId.equals("null")) { // 不为空的情况下
				imageUrl = tencentOssOrigin + "javanotes/" + imageId + ".jpg";
			}
			data.put("imageUrl", imageUrl);
			
			data.put("timestamp", timestamp);
			
			consequent.setSuccess().setData(data);
		} else {
			consequent.setMessage("创建一个笔记SQL执行失败");
		}

		return consequent;
	}
}
