package cn.rejiejay.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.dataaccessobject.AndroidRecordEventRepository;
import cn.rejiejay.dataaccessobject.AndroidRecordEvents;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.viewobject.AndroidAddRecordReque;
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

	/**
	 * 统计 所有 list 数据
	 */
	public long listRecordEventCount() {
		return androidRecordEventRepository.count();
	}

	/**
	 * 获取 列表根据时间
	 * 
	 * @param pageNo
	 */
	public Consequencer getRecordEventListByTime(int pageNo) {
		Consequencer consequent = new Consequencer();

		/**
		 * 因为是从零开始的
		 */
		if (pageNo >= 1) {
			pageNo = pageNo - 1;
		}

		int startNum = pageNo * 10;

		List<AndroidRecordEvents> recordEventResult = androidRecordEventRepository.findRecordEventByPageNo(startNum);

		logger.debug(
				"androidRecordEventRepository.findRecordEventByPageNo(" + pageNo + "):" + recordEventResult.toString());

		// 判断是否查询到数据
		if (recordEventResult.size() <= 0) {
			return consequent.setMessage("查询数据为空！");
		}

		// 数据转换(不进行转换，有些数据会返回null
		JSONArray recordEventArray = new JSONArray();
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
			item.put("imageidentity", imageidentity != null ? imageidentity : "");
			item.put("imageurl", "");

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

		JSONObject data = new JSONObject();
		data.put("list", JSONArray.parseArray(JSON.toJSONString(recordEventArray)));

		return consequent.setSuccess(data);
	}

	/**
	 * 新增记录
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
		
		int insertRecordResult = androidRecordEventRepository.insertRecord(recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week);

		if (insertRecordResult == 1) {
			return consequent.setSuccess();
		} else {
			return consequent.setMessage("创建一个记录SQL执行失败");
		}
		
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
	 * 编辑一条记录
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
			androidRecordEventRepository.updateRecord(androidid, recordtitle, recordmaterial, recordcontent, imageidentity, tag);
		} catch (IllegalArgumentException e) {
			return consequent.setMessage("更新  Android Record id:“" + androidid + "”失败，原因：" + e.toString());
		}

		return consequent.setSuccess();
	}
}
