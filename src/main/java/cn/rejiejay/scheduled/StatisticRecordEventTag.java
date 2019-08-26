package cn.rejiejay.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.rejiejay.service.AndroidServer;
import cn.rejiejay.utils.Consequencer;

/**
 * 持久化统计 Android 记录事件 标签
 * 
 * @author _rejiejay
 * @Date 2019年8月26日21:30:04
 */
@Component
public class StatisticRecordEventTag {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AndroidServer androidServer;

	/**
	 * 每天凌晨1点30执行 持久化统计 Android 记录事件 标签
	 */
	@Scheduled(cron = "0 30 1 * * ? *")
	public void cleanUploade() {
		Consequencer consequent = androidServer.rersisStatisRecordEventTag();
		if (consequent.getResult() != 1) {
			logger.error(" 持久化统计 Android 记录事件 标签 失败");
		}
	}
}
