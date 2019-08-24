package cn.rejiejay.service;

import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.viewobject.AddRecordReque;

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
	public Consequencer getRecordEventListByTime(int pageNo);

	/**
	 * 新增记录
	 */
	public Consequencer addRecord(AddRecordReque record);
}
