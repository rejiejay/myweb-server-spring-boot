package cn.rejiejay.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
		MvcResult result = mockMvc.perform(get("/android/recordevent/list/"))  // 简单的get 请求
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

		MvcResult addJavaNoteResult = mockMvc.perform(MockMvcRequestBuilders.post("/android/record/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8).header("x-rejiejay-authorization", signature)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes())).andReturn();

		System.out.println("\n 新增记录： " + addJavaNoteResult.getResponse().getContentAsString() + "\n");
	}
}
