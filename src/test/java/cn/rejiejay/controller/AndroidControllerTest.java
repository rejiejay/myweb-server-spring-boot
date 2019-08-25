package cn.rejiejay.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.sql.Date;
import java.util.Calendar;

/**
 * 安卓端支持（部分支持web端
 * 
 * @author _rejeijay
 * @Date 2019年6月29日16:06:08
 */
public class AndroidControllerTest extends BaseControllerTests {
	/**
	 * 测试 获取第一页 安卓端
	 */
	@Test
	public void testGetAndroidRecordEvent() throws Exception {
		MvcResult result = mockMvc.perform(get("/android/recordevent/list/")) // 简单的get 请求
				.andReturn(); // 返回执行请求的结果

		System.out.println(result.getResponse().getContentAsString() + "\n");
	}

	/**
	 * 测试 新增
	 */
	@Test
	public void testAddRecord() throws Exception {
		JSONObject jsonObjReq = new JSONObject();
		jsonObjReq.put("tag", "tag");
		jsonObjReq.put("timestamp", 1566658090235L);
		jsonObjReq.put("fullyear", 2018);
		jsonObjReq.put("month", 8);
		jsonObjReq.put("week", 2);
		jsonObjReq.put("imageidentity", "");
		jsonObjReq.put("recordtitle", "recordtitlerecordtitle");
		jsonObjReq.put("recordmaterial", "recordmaterialrecordmaterial");
		jsonObjReq.put("recordcontent", "recordcontentrecordcontent");

		String signature = this.createSignature(JSON.toJSONString(jsonObjReq));

		if (signature == null) {
			System.out.println("\n 生成签名错误 \n");
			return;
		}

		MvcResult addAndroidItem = mockMvc.perform(MockMvcRequestBuilders.post("/android/record/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8).header("x-rejiejay-authorization", signature)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes())).andReturn();

		System.out.println("\n 新增记录： " + addAndroidItem.getResponse().getContentAsString() + "\n");
	}

	@Test
	public void TestOkle() {
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date(1535124901000L));

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		String week_name = weeks[week_index];

		int day = cal.get(Calendar.DAY_OF_MONTH);

		System.out.println(
				"\n " + String.valueOf(year) + String.valueOf(month) + week_name + String.valueOf(day) + " \n");
	}
	
	/**
	 * 测试 删除
	 */
	@Test
	public void testDelAndroidItem() throws Exception {
		JSONObject jsonObjReq = new JSONObject();
		jsonObjReq.put("androidid", 8);
		
		String signature = this.createSignature(jsonObjReq.toJSONString());

		if (signature == null) {
			System.out.println("\n 生成签名错误 \n");
			return;
		}

		MvcResult delAndroidItem = mockMvc.perform(MockMvcRequestBuilders.post("/android/recordevent/del")
				.contentType(MediaType.APPLICATION_JSON_UTF8).header("x-rejiejay-authorization", signature)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes())).andReturn();

		System.out.println("\n 删除记录： " + delAndroidItem.getResponse().getContentAsString() + "\n");
		
	}
	
	/**
	 * 测试 编辑
	 */
	@Test
	public void testEditAndroidRecord() throws Exception {
		JSONObject jsonObjReq = new JSONObject();
		jsonObjReq.put("androidid", 1);
		jsonObjReq.put("tag", "tagtag");
		jsonObjReq.put("imageidentity", "");
		jsonObjReq.put("recordtitle", "recordtitlerecordtitle");
		jsonObjReq.put("recordmaterial", "素材-思路-联想-aa-sss-vvv-fff-ddd");
		jsonObjReq.put("recordcontent", "recordcontentrecordcontent");
		
		String signature = this.createSignature(jsonObjReq.toJSONString());

		if (signature == null) {
			System.out.println("\n 生成签名错误 \n");
			return;
		}

		MvcResult delAndroidItem = mockMvc.perform(MockMvcRequestBuilders.post("/android/record/edit")
				.contentType(MediaType.APPLICATION_JSON_UTF8).header("x-rejiejay-authorization", signature)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes())).andReturn();

		System.out.println("\n 编辑记录： " + delAndroidItem.getResponse().getContentAsString() + "\n");
		
	}
}
