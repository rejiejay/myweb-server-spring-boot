package cn.rejiejay.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 拦截器测试
 * 
 * @author _rejeijay
 * @Date 2019年6月29日16:06:08
 */
public class SecurityPreHandleTest extends BaseControllerTests {

	// 测试拦截器错误的Get请求
	@Test
	public void SecurityErrorGetTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/security?username=rejiejay&password=DFqew1938167"))  // 简单的get 请求
				.andReturn(); // 返回执行请求的结果
		
		System.out.println("返回执行请求的结果:" + result.getResponse().getContentAsString());
	}

	// 测试拦截器错误的Post请求
	@Test
	public void SecurityErrorPostTest() throws Exception {
		String reqStr = "{\"username\": \"username\", \"password\": \"DFqew1938167\"}"; 
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/security")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.content(reqStr.getBytes())
			)
			.andReturn(); // 返回执行请求的结果
		
		System.out.println("返回执行请求的结果:" + result.getResponse().getContentAsString());
	}
}
