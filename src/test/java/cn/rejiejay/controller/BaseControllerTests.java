package cn.rejiejay.controller;


import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import cn.rejiejay.security.SecurityRiskRequestWrapperFilter;
import cn.rejiejay.service.UserServer;
import cn.rejiejay.utils.Consequencer;
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
	public String createSignature(String reqParam) throws Exception {
		// 操作ORM获取数据库数据
		Consequencer loginResult = userServer.authorizeRejiejay("1938167");
		
		// 判断结果是否正确
		if (loginResult.getResult() != 1) { // 不正确的情况下，直接返回错误结果
			return null;
		}
		
		System.out.println("\n rejiejay登录授权： " + loginResult.getJsonStringMessage() + "\n");
		
		String token = loginResult.getData().getString("token");

		String signature = DigitalSignature.EncryptSignature(reqParam, "rejiejay", token); // 生成签名
		
		System.out.println("\n rejiejay登录授权生成签名： " + signature + "\n");
		
		return signature;
	}
}
