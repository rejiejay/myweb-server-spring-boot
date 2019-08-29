package cn.rejiejay.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.dataaccessobject.AndroidRecordEventRepository;
import cn.rejiejay.dataaccessobject.AndroidRecordEventTag;
import cn.rejiejay.dataaccessobject.AndroidRecordEventTagRepository;
import cn.rejiejay.dataaccessobject.AndroidRecordEvents;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.viewobject.AndroidAddEventReque;
import cn.rejiejay.viewobject.AndroidAddRecordReque;
import cn.rejiejay.viewobject.AndroidEditEventReque;
import cn.rejiejay.viewobject.AndroidEditRecordReque;

/**
 * 安卓端支持（部分支持web端 实现类
 * 
 * @author _rejeijay
 * @Date 2019-7-16 16:11:38
 */
@Service
public class AndroidServerImpl implements AndroidServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AndroidRecordEventRepository androidRecordEventRepository;

	@Autowired
	private AndroidRecordEventTagRepository androidRecordEventTagRepository;

	@Value("${rejiejay.tencentoss.origin}")
	private String tencentOssOrigin;

	// 记录事件数据 转换 方法
	public class ConversionRecordEvent {
		ConversionRecordEvent(List<AndroidRecordEvents> recordEventResult, JSONArray recordEventArray) {
			recordEventResult.forEach(recordEvent -> {
				JSONObject item = new JSONObject();
				item.put("androidid", recordEvent.getAndroidid());
				item.put("type", recordEvent.getType());
				item.put("timestamp", recordEvent.getTimestamp());
				item.put("fullyear", recordEvent.getFullyear());
				item.put("month", recordEvent.getMonth());
				item.put("week", recordEvent.getWeek());

				String tag = recordEvent.getTag();
				item.put("tag", tag != null ? tag : "");

				/**
				 * 这里图片需要转换， 但是图片的上传方法还没写暂时不写
				 */
				String imageidentity = recordEvent.getImageidentity();
				if (imageidentity != null && imageidentity.length() > 0) {
					item.put("imageidentity", imageidentity);
					item.put("imageurl", tencentOssOrigin + "android/" + imageidentity + ".png");
				} else {
					item.put("imageidentity", "");
					item.put("imageurl", "");
				}

				// 判断空
				String eventcause = recordEvent.getEventcause();
				item.put("eventcause", eventcause != null ? eventcause : "");
				String eventconclusion = recordEvent.getEventconclusion();
				item.put("eventconclusion", eventconclusion != null ? eventconclusion : "");
				String eventprocess = recordEvent.getEventprocess();
				item.put("eventprocess", eventprocess != null ? eventprocess : "");
				String eventresult = recordEvent.getEventresult();
				item.put("eventresult", eventresult != null ? eventresult : "");

				String recordtitle = recordEvent.getRecordtitle();
				item.put("recordtitle", recordtitle != null ? recordtitle : "");
				String recordmaterial = recordEvent.getRecordmaterial();
				item.put("recordmaterial", recordmaterial != null ? recordmaterial : "");
				String recordcontent = recordEvent.getRecordcontent();
				item.put("recordcontent", recordcontent != null ? recordcontent : "");

				recordEventArray.add(item);
			});
		}
	}

	/**
	 * 统计 所有 list 数据
	 */
	public long listRecordEventCount() {
		return androidRecordEventRepository.count();
	}

	/**
	 * 获取 列表根据时间
	 */
	public Consequencer getRecordEventListByTime(String dataType, String dataTag, int pageNo) {
		Consequencer consequent = new Consequencer();

		/**
		 * 因为是从零开始的
		 */
		if (pageNo >= 1) {
			pageNo = pageNo - 1;
		}

		int startNum = pageNo * 10;

		List<AndroidRecordEvents> recordEventResult;
		if (dataType.equals("all") && dataTag.equals("all")) {
			recordEventResult = androidRecordEventRepository.findRecordEventByPageNo(startNum);

		} else if (!dataType.equals("all") && dataTag.equals("all")) {
			recordEventResult = androidRecordEventRepository.findTypeRecordEventByPageNo(dataType, startNum);

		} else if (dataType.equals("all") && !dataTag.equals("all")) {
			recordEventResult = androidRecordEventRepository.findTagRecordEventByPageNo(dataTag, startNum);

		} else {
			recordEventResult = androidRecordEventRepository.findTypeTagRecordEventByPageNo(dataType, dataTag,
					startNum);

		}

		logger.debug(
				"androidRecordEventRepository.findRecordEventByPageNo(" + pageNo + "):" + recordEventResult.toString());

		// 判断是否查询到数据
		if (recordEventResult.size() <= 0) {
			return consequent.setMessage("查询数据为空！");
		}

		// 数据转换(不进行转换，有些数据会返回null
		JSONArray recordEventArray = new JSONArray();
		new ConversionRecordEvent(recordEventResult, recordEventArray);

		JSONObject data = new JSONObject();
		data.put("list", JSONArray.parseArray(JSON.toJSONString(recordEventArray)));

		return consequent.setSuccess(data);
	}

	/**
	 * 获取 列表 根据时间戳范围
	 */
	public Consequencer listRecordEventByTime(long minTimestamp, long maxTimestamp, int pageNo) {
		Consequencer consequent = new Consequencer();

		/**
		 * 因为是从零开始的
		 */
		if (pageNo >= 1) {
			pageNo = pageNo - 1;
		}

		List<AndroidRecordEvents> recordEventResult = androidRecordEventRepository.listRecordEventByTime(minTimestamp,
				maxTimestamp, pageNo);

		// 判断是否查询到数据
		if (recordEventResult.size() <= 0) {
			return consequent.setMessage("查询数据为空！");
		}

		// 数据转换(不进行转换，有些数据会返回null
		JSONArray recordEventArray = new JSONArray();
		new ConversionRecordEvent(recordEventResult, recordEventArray);

		JSONObject data = new JSONObject();
		data.put("list", JSONArray.parseArray(JSON.toJSONString(recordEventArray)));

		return consequent.setSuccess(data);
	}

	/**
	 * 统计 时间段 有多少条数据
	 */
	public int countRecordEventTimestamp(long minTimestamp, long maxTimestamp) {
		return androidRecordEventRepository.countRecordEventTimestamp(minTimestamp, maxTimestamp);
	}

	/**
	 * 获取 列表 随机
	 */
	public Consequencer getRecordEventListByRandom(String dataType, String dataTag, int count) {
		Consequencer consequent = new Consequencer();

		List<AndroidRecordEvents> recordEventResult;
		if (dataType.equals("all") && dataTag.equals("all")) {
			recordEventResult = androidRecordEventRepository.findRecordEventByRandom(count);

		} else if (!dataType.equals("all") && dataTag.equals("all")) {
			recordEventResult = androidRecordEventRepository.findTypeRecordEventByRandom(dataType, count);

		} else if (dataType.equals("all") && !dataTag.equals("all")) {
			recordEventResult = androidRecordEventRepository.findTagRecordEventByRandom(dataTag, count);

		} else {
			recordEventResult = androidRecordEventRepository.findTypeTagRecordEventByRandom(dataType, dataTag, count);

		}

		logger.debug(
				"androidRecordEventRepository.findRecordEventByRandom(" + count + "):" + recordEventResult.toString());

		// 判断是否查询到数据
		if (recordEventResult.size() <= 0) {
			return consequent.setMessage("查询数据为空！");
		}

		// 数据转换(不进行转换，有些数据会返回null
		JSONArray recordEventArray = new JSONArray();
		new ConversionRecordEvent(recordEventResult, recordEventArray);

		JSONObject data = new JSONObject();
		data.put("list", JSONArray.parseArray(JSON.toJSONString(recordEventArray)));

		return consequent.setSuccess(data);
	}

	/**
	 * 根据id获取记录
	 */
	public Consequencer getRecordEventBy(int id) {
		Consequencer consequent = new Consequencer();

		Optional<AndroidRecordEvents> recordEvent = androidRecordEventRepository.findById(Long.valueOf(id));

		logger.info("javaNotesRepository.findById(" + id + "): " + recordEvent.toString()); // 打印数据库获取的数据

		AndroidRecordEvents oneRecordEvent = null;

		try {
			oneRecordEvent = recordEvent.get();
		} catch (Exception e) {
			return consequent.setMessage("查询数据为空！");
		}

		JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(oneRecordEvent));

		return consequent.setSuccess(data);
	}

	/**
	 * 根据id删除数据
	 */
	public Consequencer delRecordEventBy(int id) {
		Consequencer consequent = new Consequencer();

		logger.info("删除 Android RecordEvent 根据 id" + id); // 打印数据库获取的数据

		try {
			androidRecordEventRepository.deleteById(Long.valueOf(id));
		} catch (IllegalArgumentException e) {
			return consequent.setMessage("删除 Android RecordEvent id:“" + id + "”失败，原因：" + e.toString());
		}

		return consequent.setSuccess();
	}

	/**
	 * 新增 记录
	 */
	public Consequencer addRecord(AndroidAddRecordReque record) {
		Consequencer consequent = new Consequencer();

		String recordtitle = record.getRecordtitle();
		String recordmaterial = record.getRecordmaterial();
		String recordcontent = record.getRecordcontent();
		String imageidentity = record.getImageidentity();
		String tag = record.getTag();
		long timestamp = record.getTimestamp();
		int fullyear = record.getFullyear();
		int month = record.getMonth();
		int week = record.getWeek();

		int insertRecordResult = androidRecordEventRepository.insertRecord(recordtitle, recordmaterial, recordcontent,
				imageidentity, tag, timestamp, fullyear, month, week);

		if (insertRecordResult == 1) {
			return consequent.setSuccess();
		} else {
			return consequent.setMessage("创建一个记录SQL执行失败");
		}

	}

	/**
	 * 编辑 记录
	 */
	public Consequencer editRecord(AndroidEditRecordReque editRecord) {
		Consequencer consequent = new Consequencer();

		int androidid = editRecord.getAndroidid();
		String recordtitle = editRecord.getRecordtitle();
		String recordmaterial = editRecord.getRecordmaterial();
		String recordcontent = editRecord.getRecordcontent();
		String imageidentity = editRecord.getImageidentity();
		String tag = editRecord.getTag();

		try {
			androidRecordEventRepository.updateRecord(androidid, recordtitle, recordmaterial, recordcontent,
					imageidentity, tag);
		} catch (IllegalArgumentException e) {
			return consequent.setMessage("更新  Android Record id:“" + androidid + "”失败，原因：" + e.toString());
		}

		return consequent.setSuccess();
	}

	/**
	 * 新增 事件
	 */
	public Consequencer addEvent(AndroidAddEventReque event) {
		Consequencer consequent = new Consequencer();

		long timestamp = event.getTimestamp();
		int fullyear = event.getFullyear();
		int month = event.getMonth();
		int week = event.getWeek();
		String imageidentity = event.getImageidentity();
		String eventcause = event.getEventcause();
		String eventprocess = event.getEventprocess();
		String eventresult = event.getEventresult();
		String eventconclusion = event.getEventconclusion();
		
		int insertEventResult = androidRecordEventRepository.insertEvent(eventcause, eventprocess, eventresult, eventconclusion, imageidentity, timestamp, fullyear, month, week);

		if (insertEventResult == 1) {
			return consequent.setSuccess();
		} else {
			return consequent.setMessage("创建一个事件SQL执行失败");
		}
	}

	/**
	 * 编辑 事件
	 */
	public Consequencer editEvent(AndroidEditEventReque event) {
		Consequencer consequent = new Consequencer();

		long timestamp = event.getTimestamp();
		int fullyear = event.getFullyear();
		int month = event.getMonth();
		int week = event.getWeek();
		int androidid = event.getAndroidid();
		String imageidentity = event.getImageidentity();
		String eventcause = event.getEventcause();
		String eventprocess = event.getEventprocess();
		String eventresult = event.getEventresult();
		String eventconclusion = event.getEventconclusion();


		try {
			androidRecordEventRepository.updateEvent(androidid, eventcause, eventprocess, eventresult, eventconclusion, imageidentity, timestamp, fullyear, month, week);
		} catch (IllegalArgumentException e) {
			return consequent.setMessage("更新  Android Event id:“" + androidid + "”失败，原因：" + e.toString());
		}

		return consequent.setSuccess();
	}

	/**
	 * 获取 记录标签
	 */
	public Consequencer getRecordEventTag() {
		Consequencer consequent = new Consequencer();

		Iterable<AndroidRecordEventTag> recordEventTagResult = androidRecordEventTagRepository.findAll();

		if (recordEventTagResult == null) {
			return consequent.setMessage("数据为空");
		}

		List<AndroidRecordEventTag> recordEventTagList = new ArrayList<AndroidRecordEventTag>();
		recordEventTagResult.forEach(recordEventTagItem -> {
			recordEventTagList.add(recordEventTagItem);
		});

		if (recordEventTagList.size() < 1) {
			return consequent.setMessage("数据为空");
		}

		JSONObject data = new JSONObject();
		JSONArray list = JSONObject.parseArray(JSONObject.toJSONString(recordEventTagList));
		data.put("list", list);

		return consequent.setSuccess(data);
	}

	/**
	 * 新增 记录标签
	 */
	public Consequencer addRecordEventTag(String tag) {
		Consequencer consequent = new Consequencer();

		try {
			androidRecordEventTagRepository.insertRecordEventTag(tag);
		} catch (IllegalArgumentException e) {
			return consequent.setMessage("新增 Android RecordEvent tag:“" + tag + "”失败，原因：" + e.toString());
		}

		return consequent.setSuccess();
	}

	/**
	 * 删除 记录标签
	 */
	public Consequencer delRecordEventTag(int id) {
		Consequencer consequent = new Consequencer();

		try {
			androidRecordEventTagRepository.deleteById(Long.valueOf(id));
		} catch (IllegalArgumentException e) {
			return consequent.setMessage("删除 Android RecordEvent Tag id:“" + id + "”失败，原因：" + e.toString());
		}

		return consequent.setSuccess();
	}

	/**
	 * 统计 记录标签
	 */
	public List<String> statisticRecordEventTag() {
		List<String> statisticTagResult = androidRecordEventRepository.statisticRecordEventTag();

		List<String> resultList = new ArrayList<String>();
		statisticTagResult.forEach(item -> {
			if (item != null && item.length() > 0) {
				resultList.add(item);
			}
		});

		return resultList;
	}

	/**
	 * 持久化 生成 统计 记录标签
	 */
	public Consequencer rersisStatisRecordEventTag() {
		Consequencer consequent = new Consequencer();

		// 执行清空表操作
		try {
			androidRecordEventTagRepository.deleteAll();
		} catch (IllegalArgumentException e) {
			return consequent.setMessage("清空 Android RecordEvent Tag 失败，原因：" + e.toString());
		}

		// 统计 记录标签
		Boolean haveError = false;
		List<String> resultList = statisticRecordEventTag();
		for (int i = 0; i < resultList.size(); i++) {
			Consequencer addTagResult = addRecordEventTag(resultList.get(i));

			if (addTagResult.getResult() != 1) {
				haveError = true;
			}
		}
		if (haveError) {
			return consequent.setMessage("统计 Android RecordEvent Tag 失败，原因：添加失败！");
		}

		return consequent.setSuccess();
	}
}
