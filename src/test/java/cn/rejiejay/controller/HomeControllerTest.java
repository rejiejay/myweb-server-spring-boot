package cn.rejiejay.controller;

import org.junit.Test;

import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * 主页测试
 * 
 * @author _rejeijay
 * @Date 2019年6月29日16:06:08
 */
public class HomeControllerTest extends BaseControllerTests {
	@Test
	public void testHomeGet() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/"))  // 简单的get 请求
				.andReturn(); // 返回执行请求的结果
		
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testHomePost() throws Exception {
		MvcResult result = mockMvc.perform(post("/"))  // 简单的post 请求
				.andReturn(); // 返回执行请求的结果
		
		System.out.println(result.getResponse().getContentAsString());
	}
}
