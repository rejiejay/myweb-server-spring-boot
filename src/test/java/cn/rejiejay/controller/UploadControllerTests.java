package cn.rejiejay.controller;

import java.io.File;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UploadControllerTests extends BaseControllerTests {

	/**
	 * 测试上传字符串
	 */
	@Test
	public void TestUploadString() throws Exception {
		String reqStr = "1938167"; // 密码

		// 返回执行请求的结果
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/upload/").contentType(MediaType.valueOf("text/plain;charset=UTF-8"))
						.header("x-rejiejay-authorization", "1938167").accept(MediaType.APPLICATION_JSON_UTF8)
						.content(reqStr.getBytes()))
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}

	/**
	 * _测试创建文件
	 */
	@Test
	public void createNewFile() throws Exception {
		File file = new File("/app.js");

		file.createNewFile();
	}
}