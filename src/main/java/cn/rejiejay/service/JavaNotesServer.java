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
	public long getAllNotesCount();

	/**
	 * 根据 页码分页 获取10条 JAVA Notes 
	 */
	public Consequencer getNotesByTime(int pageNo);

	/**
	 * 随机获取10条 JAVA Notes 
	 */
	public Consequencer getNotesByRandom(int total);

	/**
	 * 获取 JAVA Notes 根据 id
	 */
	public Consequencer getNoteById(long id);

	/**
	 * 删除 JAVA Notes 根据 id
	 */
	public Consequencer delNoteById(long id);
}
