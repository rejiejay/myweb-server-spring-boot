package cn.rejiejay.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;

import cn.rejiejay.dataaccessobject.AndroidRecordEventRepository;
import cn.rejiejay.utils.Consequencer;

/**
 * Created by _rejiejay Date 2019年7月5日16:51:27 Description:测试类
 */
public class AndroidControllerTest extends BaseServiceTests {

	@Autowired
	private AndroidServer androidServer;

	@Autowired
	private AndroidServerStatistics androidServerStatistics;
	
	@Autowired
	private AndroidRecordEventRepository androidRecordEventRepository;

	// 统计标签
	@Test
	public void statisticRecordEventTag() {
		List<String> consequent = androidServer.statisticRecordEventTag();

		System.out.println("androidServer.statisticRecordEventTag:" + consequent.toString());
	}

	// 持久化统计标签
	@Test
	public void rersisStatisRecordEventTag() {
		Consequencer consequent = androidServer.rersisStatisRecordEventTag();

		System.out.println("androidServer.statisticRecordEventTag:" + consequent.getJsonStringMessage());
	}

	// 持久化 安卓端 日期统计
	// 18年（7条
	// 	秋季（2条
	// 		9月（2条
	// 			第一周（2条
	// 				18年秋9月第一周 2018-9-1 1535731200000
	// 				18年秋9月第一周 2018-9-2 1535817600000
	// 	冬季（5条
	// 		12月（1条
	// 			第一周（1条
	// 				18年秋12月第一周 2018-12-1 1543593600000
	// 		11月（4条
	// 			第1周（1条
	// 				18年秋9月第一周 2018-11-1 1541001600000
	// 			第2周（1条
	// 				18年秋9月第一周 2018-11-8 1541606400000
	// 			第3周（1条
	// 				18年秋9月第一周 2018-11-15 1542240000000
	// 			第4周（1条
	// 				18年秋9月第一周 2018-11-24 1543017600000
	// 19年（1条
	// 	春季（1条
	// 		1月（1条
	// 			第一周（1条
	// 			18年秋9月第一周 2019-1-2 1546358400000
	//	insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values ("record", "18年秋9月第一周 2018-9-1 1535731200000", "18年秋9月第一周 2018-9-1 1535731200000", "18年秋9月第一周 2018-9-1 1535731200000", "", "", 1535731200000, 2018, 9, 1);
	//	insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values ("record", "18年秋9月第一周 2018-9-2 1535817600000", "18年秋9月第一周 2018-9-2 1535817600000", "18年秋9月第一周 2018-9-2 1535817600000", "", "", 1535817600000, 2018, 9, 2);
	//	insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values ("record", "18年秋12月第一周 2018-12-1 1543593600000", "18年秋12月第一周 2018-12-1 1543593600000", "18年秋12月第一周 2018-12-1 1543593600000", "", "", 1543593600000, 2018, 12, 1);
	//	insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values ("record", "18年秋9月第一周 2018-11-1 1541001600000", "18年秋9月第一周 2018-11-1 1541001600000", "18年秋9月第一周 2018-11-1 1541001600000", "", "", 1541001600000, 2018, 11, 1);
	//	insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values ("record", "18年秋9月第一周 2018-11-8 1541606400000", "18年秋9月第一周 2018-11-8 1541606400000", "18年秋9月第一周 2018-11-8 1541606400000", "", "", 1541606400000, 2018, 11, 8);
	//	insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values ("record", "18年秋9月第一周 2018-11-15 1542240000000", "18年秋9月第一周 2018-11-15 1542240000000", "18年秋9月第一周 2018-11-15 1542240000000", "", "", 1542240000000, 2018, 11, 15);
	//	insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values ("record", "18年秋9月第一周 2018-11-24 1543017600000", "18年秋9月第一周 2018-11-24 1543017600000", "18年秋9月第一周 2018-11-24 1543017600000", "", "", 1543017600000, 2018, 11, 24);
	//	insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values ("record", "18年秋9月第一周 2019-1-2 1546358400000", "18年秋9月第一周 2019-1-2 1546358400000", "18年秋9月第一周 2019-1-2 1546358400000", "", "", 1546358400000, 2019, 1, 2);
	@Test
	public void StatisticsTimeToJSON() {
		Consequencer consequent = androidServerStatistics.StatisticsTimeToJSON();

		System.out.println("androidServer.statisticRecordEventTag:" + consequent.getJsonStringMessage());
	}

	// 星期 （ 持久化 安卓端 日期统计 
	@Test
	public void StatisticsWeekToJSON() {
		JSONArray weekStatistic = androidServerStatistics.statisticWeek(2018, 9);
		System.out.println("androidServer.statisticRecordEventTag:" + weekStatistic.toJSONString());
	}

	// 月 （持久化 安卓端 日期统计
	@Test
	public void StatisticsMonthToJSON() {
		JSONArray MonthStatistic = androidServerStatistics.statisticMonth(2018, 9);
		System.out.println("androidServer.statisticMonth:" + MonthStatistic.toJSONString());
	}
	
	// 季度 （持久化 安卓端 日期统计
	@Test
	public void StatisticsSeasonToJSON() {
		JSONArray SeasonStatistic = androidServerStatistics.statisticSeason(2018);
		System.out.println("androidServer.statisticSeason:" + SeasonStatistic.toJSONString());
	}

	// 测试SQL
	@Test
	public void testAndroidRecordEventRepository() {
		int yearCount = androidRecordEventRepository.countRecordEventFullyear(2018);

		System.out.println("testAndroidRecordEventRepository:" + yearCount);
	}
	
	// 测试自己的代码
	@Test
	public void TestCode() {
		String dateString = "2018-02-23"; 
		try {
			Date date =  new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

			System.out.println("\n /n TestCode:" + date.getTime());
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
	// 测试读取本地统计文件
	@Test
	public void testDownloadStatistic() {
		Consequencer StatisticResult = androidServerStatistics.downloadStatistic();
		System.out.println("androidServerStatistics.downloadStatistic:" + StatisticResult.getJsonStringMessage());
		
	}
}
