package cn.rejiejay.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rejiejay.utils.Consequencer;

/**
 * Created by _rejiejay Date 2019年7月5日16:51:27 Description:测试类
 */
public class JavaNotesTest extends BaseServiceTests {

	@Autowired
	private JavaNotesServer javaNotesServer;

	// 测试新增一条记录
	@Test
	public void testUploadJavaNotes() {
		String imageName = String.valueOf(new Date().getTime());
		String title = "上传测试title";
		String htmlContent = "上传测试htmlContent";

		Consequencer consequent = javaNotesServer.uploadJavaNotes(title, imageName, htmlContent);

		System.out.println("javaNotesServer.uploadJavaNotes:" + consequent.getJsonStringMessage());
	}

	// 测试获取所有记录
	@Test
	public void testGetAllJavaNotesCount() {
		System.out.println("\n javaNotesServer.getAllNotesCount:" + javaNotesServer.getAllNotesCount() + "\n");
	}

	// 测试获取10条 第一页JAVA Notes 
	@Test
	public void testGetOnePageNotes() {
		Consequencer consequent = javaNotesServer.getNotesByTime(1);

		System.out.println("\n javaNotesServer.getNotesByTime(1):" + consequent.getJsonStringMessage() + "\n");
	}
}
