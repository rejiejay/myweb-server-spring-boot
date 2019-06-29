package cn.rejiejay.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.WebApplicationContext;

/**
 * 主页测试
 * 
 * @author _rejeijay
 * @Date 2019年6月29日16:06:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseControllerTests {
	@Autowired
	private WebApplicationContext wac; // 注入WebApplicationContext

	public MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

	//  @Autowired  
	//  private MockHttpSession session;// 注入模拟的_http session  

	//  @Autowired  
	//  private MockHttpServletRequest request;// 注入模拟的_http request\ 

	@Before // 在测试开始前初始化工作
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		System.out.println("\u001B[31m -----------------开始测试----------------- \n");
	}

	@After
	public void after() {
		System.out.println("\u001B[31m -----------------测试结束----------------- \n \u001B[0m");
	}
}
