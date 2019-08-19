package cn.rejiejay.service;

import java.util.List;

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

		List<AndroidRecordEvents> javaNotesResult = androidRecordEventRepository.findRecordEventByPageNo(startNum);

		logger.debug(
				"androidRecordEventRepository.findRecordEventByPageNo(" + pageNo + "):" + javaNotesResult.toString());

		// 判断是否查询到数据
		if (javaNotesResult.size() <= 0) {
			return consequent.setMessage("查询数据为空！");
		}

		JSONObject data = new JSONObject();
		data.put("list", JSONArray.parseArray(JSON.toJSONString(javaNotesResult)));

		return consequent.setSuccess(data);
	}
}
