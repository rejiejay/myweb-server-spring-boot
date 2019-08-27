package cn.rejiejay.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.dataaccessobject.AndroidRecordEventRepository;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.utils.DateFormat;

/**
 * 安卓端 日期统计
 * 
 * @author _rejeijay
 * @Date 2019-7-16 16:11:38
 */
@Service
public class AndroidServerStatistics {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${rejiejay.file.statistic-dir}")
	private String filePath;

	@Autowired
	private AndroidRecordEventRepository androidRecordEventRepository;

	public Consequencer StatisticsTimeToJSON() {
		Consequencer consequent = new Consequencer();

		// 先查询多少种年份
		List<Integer> statisticFullyearResult = androidRecordEventRepository.statisticRecordEventFullyear();

		if (statisticFullyearResult.size() <= 0) {
			return consequent.setMessage("年份为空，不需要统计");
		}

		System.out.println("statisticFullyearResult" + statisticFullyearResult.toString());

		JSONArray finalResults = new JSONArray(); // 最终的输出结果
		// [{
		// name: "18年",
		// count: 8,
		// minTimestamp: 1535731200000,
		// maxTimestamp: 1535731200000,
		// data: {...}
		// }]
		for (int i = 0; i < statisticFullyearResult.size(); i++) {
			Integer fullyear = statisticFullyearResult.get(i);

			// 查询年份的所有数据
			JSONObject yearResults = statisticFullyear(fullyear);
			finalResults.add(yearResults);
		}
		
		// 存储到本地
		Consequencer uploadResult = uploadStatistic(finalResults.toJSONString());
		if (uploadResult.getResult() != 1) {
			return uploadResult;
		}

		// 反还回去
		JSONObject data = new JSONObject();
		data.put("statistic", finalResults);
		return consequent.setSuccess(data);
	}

	// 根据年份 统计 数据
	public JSONObject statisticFullyear(int fullyear) {
		JSONObject data = new JSONObject();
		data.put("name", String.valueOf(fullyear) + "年");

		// 开始统计
		int yearCount = androidRecordEventRepository.countRecordEventFullyear(fullyear);
		data.put("count", yearCount);
		data.put("minTimestamp", DateFormat.getTimeByyyyyMMdd(fullyear, 1, 1));
		data.put("maxTimestamp", DateFormat.getTimeByyyyyMMdd(fullyear + 1, 1, 1));

		// 统计 季度
		// [{
		// name: "春季",
		// count: 8,
		// minTimestamp: 1535731200000,
		// maxTimestamp: 1535731200000,
		// data: {...}
		// }]
		JSONArray season = statisticSeason(fullyear);
		data.put("data", season);

		return data;
	}
	
	// 统计 季度
	public JSONArray statisticSeason(int fullyear) {
		JSONArray statisticSeasonArray = new JSONArray();
		
		List<String> season = new ArrayList<String>(); 
		season.add("春季");
		season.add("夏季");
		season.add("秋季");
		season.add("冬季");
		for (int i = 0; i < season.size(); i++) {
			JSONObject item = new JSONObject();
			
			// 名称
			item.put("name", season.get(i));
			
			// 时间戳
			int minMonth = i * 3 + 1; // 1 4 7 10
			int maxMonth = i * 3 + 4;
			long minTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, minMonth, 1);
			long maxTimestamp;
			if (maxMonth == 13) {
				maxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear + 1, 1, 1);
			} else {
				maxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, maxMonth, 1);
			}
			item.put("minTimestamp", minTimestamp);
			item.put("maxTimestamp", maxTimestamp);
			
			// 统计 季度
			int seasonCount = androidRecordEventRepository.countRecordEventTimestamp(minTimestamp, maxTimestamp);
			item.put("count", seasonCount);

			// 统计 月份
			// [{
			// name: "1月",
			// count: 8,
			// minTimestamp: 1535731200000,
			// maxTimestamp: 1535731200000,
			// data: {...}
			// }]
			JSONArray monthStatistic = statisticMonth(fullyear, minMonth);
			item.put("data", monthStatistic);
			
			statisticSeasonArray.add(item);
		}
		
		return statisticSeasonArray;
	}
	
	// 统计 月份
	public JSONArray statisticMonth(int fullyear, int minMonth) {
		JSONArray statisticMonthArray = new JSONArray();
		
		for (int i = 0; i < 3; i++) {
			JSONObject item = new JSONObject();
			int thisMonth = minMonth + i; // 1 4 7 10
			
			// 名称
			item.put("name", String.valueOf(thisMonth) + "月");
			
			// 时间戳
			long minTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth, 1);
			int nextMonth = thisMonth + 1;
			long maxTimestamp;
			if (nextMonth == 13) {
				maxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear + 1, 1, 1);
			} else {
				maxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, nextMonth, 1);
			}
			item.put("minTimestamp", minTimestamp); 
			item.put("maxTimestamp", maxTimestamp); 
			
			// 统计 月份
			int monthCount = androidRecordEventRepository.countRecordEventTimestamp(minTimestamp, maxTimestamp);
			item.put("count", monthCount);

			// 统计 周
			// [{
			// name: "第1周",
			// count: 8,
			// minTimestamp: 1535731200000,
			// maxTimestamp: 1535731200000,
			// }]
			JSONArray weekStatistic = statisticWeek(fullyear, thisMonth);
			item.put("data", weekStatistic);
			
			statisticMonthArray.add(item);
		}
		
		return statisticMonthArray;
	}
	
	// 统计 星期
	public JSONArray statisticWeek(int fullyear, int thisMonth) {
		JSONArray statisticWeekArray = new JSONArray();

		// 第一周
		JSONObject firsWeek = new JSONObject();
		// 名称
		firsWeek.put("name", "第一周"); 
		// 时间戳
		long firsMinTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth, 1);
		long firsMaxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth, 8);
		firsWeek.put("minTimestamp", firsMinTimestamp); 
		firsWeek.put("maxTimestamp", firsMaxTimestamp); 
		// 周统计
		int firsWeekCount = androidRecordEventRepository.countRecordEventTimestamp(firsMinTimestamp, firsMaxTimestamp);
		firsWeek.put("count", firsWeekCount);

		
		// 第二周
		JSONObject secondWeek = new JSONObject();
		// 名称
		secondWeek.put("name", "第二周"); 
		// 时间戳
		long secondMinTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth, 8);
		long secondMaxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth, 15);
		secondWeek.put("minTimestamp", secondMinTimestamp); 
		secondWeek.put("maxTimestamp", secondMaxTimestamp); 
		// 周统计
		int secondWeekCount = androidRecordEventRepository.countRecordEventTimestamp(secondMinTimestamp, secondMaxTimestamp);
		secondWeek.put("count", secondWeekCount);

		
		// 第三周
		JSONObject thirdWeek = new JSONObject();
		// 名称
		thirdWeek.put("name", "第三周"); 
		// 时间戳
		long thirdMinTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth, 15);
		long thirdMaxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth, 22);
		thirdWeek.put("minTimestamp", thirdMinTimestamp); 
		thirdWeek.put("maxTimestamp", thirdMaxTimestamp); 
		// 周统计
		int thirdWeekCount = androidRecordEventRepository.countRecordEventTimestamp(thirdMinTimestamp, thirdMaxTimestamp);
		thirdWeek.put("count", thirdWeekCount);

		
		// 第四周
		JSONObject fourthWeek = new JSONObject();
		// 名称
		fourthWeek.put("name", "第四周"); 
		// 时间戳
		long fourthMinTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth, 22);
		long fourthMaxTimestamp;
		if (thisMonth == 12) {
			fourthMaxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear + 1, 1, 1);
		} else {
			fourthMaxTimestamp = DateFormat.getTimeByyyyyMMdd(fullyear, thisMonth + 1, 1);
		}
		fourthWeek.put("minTimestamp", fourthMinTimestamp); 
		fourthWeek.put("maxTimestamp", fourthMaxTimestamp); 
		// 周统计
		int fourthWeekCount = androidRecordEventRepository.countRecordEventTimestamp(fourthMinTimestamp, fourthMaxTimestamp);
		fourthWeek.put("count", fourthWeekCount);

		statisticWeekArray.add(firsWeek);
		statisticWeekArray.add(secondWeek);
		statisticWeekArray.add(thirdWeek);
		statisticWeekArray.add(fourthWeek);

		return statisticWeekArray;
	}
	
	// 上传统计数据
	public Consequencer uploadStatistic(String json) {
		Consequencer consequent = new Consequencer();
		
		File file = new File(filePath + "StatisticTime.json");
		PrintStream myPrintStream = null;
		try {
			myPrintStream = new PrintStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			String msg = "统计数据成功，但是持久化失败" + e.toString();
			logger.warn(msg);
			return consequent.setMessage(msg);
		}

		myPrintStream.println(json); // 往文件里写入字符串
		myPrintStream.close(); // 记得关闭流
		
		return consequent.setSuccess();
	}
}
