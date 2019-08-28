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

	/**
	 * 判断是否存在此图片
	 */
	public Consequencer isExistsJavaNotesImage(String imageId);

	/**
	 * 删除JAVA笔记系统的图片 根据 imageId
	 */
	public Consequencer delJavaNotesImage(String imageId);

	/**
	 * 上传到Android的图片
	 */
	public Consequencer uploadAndroidImage(String imageId);

	/**
	 * 删除Android的图片 根据 imageId
	 */
	public Consequencer delAndroidImage(String imageId);

	/**
	 * 通用上传方法
	 */
	public Consequencer uploadGeneralImage(String position, String imageId, Boolean isJPG);

	/**
	 * 判断是否存在此图片
	 */
	public Consequencer isExistsImage(String position, String imageId, Boolean isJPG);

	/**
	 * 通用删除方法
	 */
	public Consequencer delGeneralImage(String position, String imageId, Boolean isJPG);
}
