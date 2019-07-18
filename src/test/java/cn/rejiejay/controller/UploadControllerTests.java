package cn.rejiejay.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UploadControllerTests extends BaseControllerTests {

	@Value("${rejiejay.file.upload-dir}")
	private String filePath;

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
	public void testCreateNewFile() throws Exception {
		File file = new File(filePath + "testCreateNewFile.txt");
		
		PrintStream myPrintStream = null;
		try {
			myPrintStream = new PrintStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			System.out.println("\n 写入文件失败: " + e.toString() + "\n");
			return;
		}
		
		myPrintStream.println("1938167"); // 往文件里写入字符串
		myPrintStream.close(); // 记得关闭流

		file.createNewFile();
	}

	/**
	 * 测试读取文件
	 */
	@Test
	public void testReadRreateFile() throws Exception {
		File file = new File(filePath + "testCreateNewFile.txt");
		
		if (!file.exists()) {
			System.out.println("\n文件读取失败，不存在此文件 \n");
			return;
		}
		

		String imageStr = ""; // 读取文件夹里面的内容

		try {
			FileInputStream in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完

			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			imageStr = new String(buffer, "UTF-8");

		} catch (IOException e) {
			System.out.println("\n读取文件失败: " + e.toString() + "\n");
			return;
		}
		

		System.out.println("\n imageStr:" + imageStr + "\n");
	}
}