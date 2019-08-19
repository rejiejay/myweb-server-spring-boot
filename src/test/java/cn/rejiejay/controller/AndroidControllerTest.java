package cn.rejiejay.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

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
	public void testAddJavaNote() throws Exception {
		MvcResult result = mockMvc.perform(get("/android/recordevent/list/"))  // 简单的get 请求
				.andReturn(); // 返回执行请求的结果
		
		System.out.println(result.getResponse().getContentAsString() + "\n");
	}
}
