package cn.rejiejay.service;

import cn.rejiejay.utils.Consequencer;

/**
 * 腾讯云对象存储 模块
 * 
 * @author _rejiejay
 * @Date 2019年7月16日16:11:16
 */
public interface OssServer {
	/**
	 * 上传到JAVA笔记系统的图片
	 */
	public Consequencer uploadJavaNotesImage(String imageId);
}
