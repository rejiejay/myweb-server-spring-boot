package cn.rejiejay.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

	/**
	 * 上传到JAVA笔记系统的图片
	 */
	public Consequencer uploadJavaNotes(String title, String imgName, String htmlContent) {
		Consequencer consequent = new Consequencer();

		long timestamp = new Date().getTime();

		logger.debug("执行SQL" + "title:" + title + ";htmlContent:" + htmlContent
				+ ";imgName:" + imgName + ";timestamp:" + timestamp);

		return consequent;
	}
}
