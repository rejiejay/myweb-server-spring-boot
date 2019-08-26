package cn.rejiejay.service;

import java.util.List;

import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.viewobject.AndroidAddRecordReque;
import cn.rejiejay.viewobject.AndroidEditRecordReque;

/**
 * 安卓端支持（部分支持web端 模块
 * 
 * @author _rejiejay
 * @Date 2019年7月16日16:11:16
 */
public interface AndroidServer {
	/**
	 * 统计 所有 list 数据
	 */
	public long listRecordEventCount();

	/**
	 * 获取 列表根据时间
	 */
	public Consequencer getRecordEventListByTime(String dataType, String dataTag, int pageNo);
	
	/**
	 * 获取 列表 随机
	 */
	public Consequencer getRecordEventListByRandom(String dataType, String dataTag, int count);

	/**
	 * 新增记录
	 */
	public Consequencer addRecord(AndroidAddRecordReque record);
	
	/**
	 * 根据id获取数据
	 */
	public Consequencer getRecordEventBy(int id);
	
	/**
	 * 根据id删除数据
	 */
	public Consequencer delRecordEventBy(int id);
	
	/**
	 * 编辑一条记录
	 */
	public Consequencer editRecord(AndroidEditRecordReque editRecord);
	
	/**
	 * 获取 持久化的 记录标签
	 */
	public Consequencer getRecordEventTag();
	
	/**
	 * 新增 持久化的 记录标签
	 */
	public Consequencer addRecordEventTag(String tag);
	
	/**
	 * 删除 持久化的 记录标签
	 */
	public Consequencer delRecordEventTag(int id);
	
	/**
	 * 统计 记录标签
	 */
	public List<String> statisticRecordEventTag();
	
	/**
	 * 持久化 生成 统计 记录标签
	 */
	public Consequencer rersisStatisRecordEventTag();
}
