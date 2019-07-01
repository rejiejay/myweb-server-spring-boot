package cn.rejiejay.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 登录页测试
 * 
 * @author _rejeijay
 * @Date 2019年6月29日16:06:08
 */
public class LoginControllerTest extends BaseControllerTests {

	// 测试登录页Post请求登录
	@Test
	public void testHomePost() throws Exception {
		String reqStr = "{\"password\": \"password\"}"; // 密码
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/user/login")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.content(reqStr.getBytes())
			)
			.andReturn(); // 返回执行请求的结果
		
		System.out.println(result.getResponse().getContentAsString());
	}

	// 测试 everyone 的授权认证
	@Test
	public void testEveryone() throws Exception {
		MvcResult result = mockMvc.perform(get("/user/everyone"))  // 简单的get 请求
				.andReturn(); // 返回执行请求的结果
		
		System.out.println(result.getResponse().getContentAsString());
	}
}
