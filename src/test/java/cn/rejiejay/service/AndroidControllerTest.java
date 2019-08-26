package cn.rejiejay.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rejiejay.utils.Consequencer;

/**
 * Created by _rejiejay Date 2019年7月5日16:51:27 Description:测试类
 */
public class AndroidControllerTest extends BaseServiceTests {

	@Autowired
	private AndroidServer androidServer;

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
}
