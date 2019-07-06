package cn.rejiejay.controller;

import org.junit.Test;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.utils.DigitalSignature;

/**
 * 登录页测试
 * 
 * @author _rejeijay
 * @Date 2019年6月29日16:06:08
 */
public class LoginControllerTest extends BaseControllerTests {

	// 测试 _rejiejay 授权登录
	@Test
	public void testRjiejayAuthorize() throws Exception {
		String reqStr = "{\"password\": \"1938167\"}"; // 密码

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/login/rejiejay").contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(reqStr.getBytes()))
				.andReturn(); // 返回执行请求的结果

		System.out.println(result.getResponse().getContentAsString());
	}

	/**
	 * 测试 _rejiejay 刷新授权登录
	 */
	@Test
	public void testRjiejayRefreshAuthorize() throws Exception {
		JSONObject jsonObjReq = new JSONObject();
		jsonObjReq.put("password", "1938167"); // 密码
		
		MvcResult authorizeResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/login/rejiejay").contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes()))
				.andReturn(); // 返回执行请求的结果

		String authorizeResultStr = authorizeResult.getResponse().getContentAsString();
		System.out.println("rejiejay登录授权： " + authorizeResultStr);

		JSONObject authorizeResultJson = JSON.parseObject(authorizeResultStr); // 序列化得到token

		String token = authorizeResultJson.getJSONObject("data").getString("token");
		
		String signature = DigitalSignature.EncryptSignature(jsonObjReq.toJSONString(), "rejiejay", token); // 生成签名

		System.out.println("rejiejay登录授权生成签名： " + signature);

		MvcResult refreshAuthorizeResult = mockMvc.perform(MockMvcRequestBuilders.post("/login/refresh/rejiejay")
				.contentType(MediaType.APPLICATION_JSON_UTF8).header("x-rejiejay-authorization", signature)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes())).andReturn(); // 返回执行请求的结果

		System.out.println("rejiejay刷新登录授权： " + refreshAuthorizeResult.getResponse().getContentAsString());
	}
}
