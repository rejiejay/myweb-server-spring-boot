package cn.rejiejay.service;

import cn.rejiejay.utils.Consequencer;

/**
 * 腾讯云对象存储 模块
 * 
 * @author _rejiejay
 * @Date 2019年7月16日16:11:16
 */
public interface JavaNotesServer {
	/**
	 * 存储一条JAVA Notes
	 */
	public Consequencer uploadJavaNotes(String title, String imageId, String htmlContent);
	

	/**
	 * 获取所有 JAVA Notes 的统计
	 */
	public Consequencer getAllNotesCount();
}
