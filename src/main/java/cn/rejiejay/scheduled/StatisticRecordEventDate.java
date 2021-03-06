package cn.rejiejay.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.rejiejay.service.AndroidServerStatistics;
import cn.rejiejay.utils.Consequencer;

/**
 * 持久化统计 Android 日期
 * 
 * @author _rejiejay
 * @Date 2019年8月27日20:46:55
 */
@Component
public class StatisticRecordEventDate {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AndroidServerStatistics androidServerStatistics;

	/**
	 * 每天凌晨1点30执行 持久化统计 Android 日期
	 */
	@Scheduled(cron = "0 30 1 * * ?")
	public void StatisticTag() {
		Consequencer consequent = androidServerStatistics.StatisticsTimeToJSON();
		if (consequent.getResult() != 1) {
			logger.error("持久化统计 Android 日期 标签 失败");
		}
	}
}
