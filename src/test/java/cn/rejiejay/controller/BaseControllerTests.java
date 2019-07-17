package cn.rejiejay.controller;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.security.SecurityRiskRequestWrapperFilter;
import cn.rejiejay.service.UserServer;
import cn.rejiejay.utils.DigitalSignature;

/**
 * 主页测试
 * 
 * @author _rejeijay
 * @Date 2019年6月29日16:06:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseControllerTests {
	@Autowired
	private WebApplicationContext wac; // 注入WebApplicationContext

	@Autowired
	private UserServer userServer;

	@Autowired
	private SecurityRiskRequestWrapperFilter filter;
	
	public MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

	// @Autowired
	// private MockHttpSession session;// 注入模拟的_http session

	// @Autowired
	// private MockHttpServletRequest request;// 注入模拟的_http request\

	@Before // 在测试开始前初始化工作
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilters(filter).build();

		System.out.println("\u001B[31m -----------------开始测试----------------- \n");
	}

	@After
	public void after() {
		System.out.println("\u001B[31m -----------------测试结束----------------- \n \u001B[0m");
	}

	/**
	 * 生成签名的方法
	 */
	@Test
	public String createSignature(String reqParam) throws Exception {
		JSONObject jsonObjReq = new JSONObject();
		jsonObjReq.put("password", "1938167"); // 密码
		
		MvcResult authorizeResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/login/rejiejay").contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes()))
				.andReturn(); // 返回执行请求的结果
		
		String authorizeResultStr = authorizeResult.getResponse().getContentAsString();

		System.out.println("\n rejiejay登录授权： " + authorizeResultStr + "\n");
		
		JSONObject authorizeResultJson = JSON.parseObject(authorizeResultStr); // 序列化得到token
		
		// 表示错误，返回
		if (authorizeResultJson.getInteger("result") != 1) {
			return null;
		}
		
		String token = authorizeResultJson.getJSONObject("data").getString("token");
		
		return null;
	}
}
