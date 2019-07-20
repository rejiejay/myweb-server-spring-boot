package cn.rejiejay.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * JAVA笔记系统测试
 * 
 * @author _rejeijay
 * @Date 2019年6月29日16:06:08
 */
public class JavaNotesControllerTest extends BaseControllerTests {

	/**
	 * 测试新增一条笔记
	 */
	@Test
	public void testAddJavaNote() throws Exception {
		JSONObject jsonObjReq = new JSONObject();

		String title = "这是测试标题";
		String imageId = "这是测试imageIdimageId";
		String htmlContent = "这是测试内容";

		jsonObjReq.put("title", title);
		jsonObjReq.put("imageId", imageId);
		jsonObjReq.put("htmlContent", htmlContent);
		
        String signature = this.createSignature(JSON.toJSONString(jsonObjReq));
        
        if (signature == null) {
        	System.out.println("\n 生成签名错误 \n");
        	return;
        }

		MvcResult addJavaNoteResult = mockMvc.perform(MockMvcRequestBuilders.post("/java/notes/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8).header("x-rejiejay-authorization", signature)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes())).andReturn();

		System.out.println("\n 新增笔记： " + addJavaNoteResult.getResponse().getContentAsString() + "\n");
	}

	/**
	 * 测试 获取第一页 时间排序的笔记
	 */
	@Test
	public void testGetOnePageNotes() throws Exception {
		MvcResult result = mockMvc.perform(get("/java/notes/list/"))  // 简单的get 请求
				.andReturn(); // 返回执行请求的结果
		
		System.out.println(result.getResponse().getContentAsString() + "\n");
	}
	
	/**
	 * 测试 获取10条随机排序的笔记
	 */
	@Test
	public void testGetRandomPageNotes() throws Exception {
		MvcResult result = mockMvc.perform(get("/java/notes/list/?sort=random"))  // 简单的get 请求
				.andReturn(); // 返回执行请求的结果
		
		System.out.println(result.getResponse().getContentAsString() + "\n");
	}
	
	/**
	 * 测试 获取1条随机排序的笔记
	 */
	@Test
	public void testGetOneNotes() throws Exception {
		MvcResult result = mockMvc.perform(get("/java/notes/get/random"))  // 简单的get 请求
				.andReturn(); // 返回执行请求的结果
		
		System.out.println(result.getResponse().getContentAsString() + "\n");
	}

	// 测试删除 JAVA Notes 根据 id
	@Test
	public void testDelNoteById() throws Exception {
		long id = 6;
		
		JSONObject jsonObjReq = new JSONObject();

		jsonObjReq.put("id", id);
		
        String signature = this.createSignature(JSON.toJSONString(jsonObjReq));

        if (signature == null) {
        	System.out.println("\n 生成签名错误 \n");
        	return;
        }

		MvcResult addJavaNoteResult = mockMvc.perform(MockMvcRequestBuilders.post("/java/notes/del")
				.contentType(MediaType.APPLICATION_JSON_UTF8).header("x-rejiejay-authorization", signature)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes())).andReturn();

		System.out.println("\n 删除笔记： " + addJavaNoteResult.getResponse().getContentAsString() + "\n");
	}

	/**
	 * 测试编辑一条笔记
	 */
	@Test
	public void testEditJavaNote() throws Exception {
		JSONObject jsonObjReq = new JSONObject();

		long id = 4;
		String title = "这是测试Edit标题";
		String imageId = ""; // 不测试图片上传， 前端再进行测试
		String htmlContent = "这是测试Edit内容";

		jsonObjReq.put("id", id);
		jsonObjReq.put("title", title);
		jsonObjReq.put("imageId", imageId);
		jsonObjReq.put("htmlContent", htmlContent);
		
        String signature = this.createSignature(JSON.toJSONString(jsonObjReq));
        
        if (signature == null) {
        	System.out.println("\n 生成签名错误 \n");
        	return;
        }

		MvcResult editJavaNoteResult = mockMvc.perform(MockMvcRequestBuilders.post("/java/notes/edit")
				.contentType(MediaType.APPLICATION_JSON_UTF8).header("x-rejiejay-authorization", signature)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(jsonObjReq.toJSONString().getBytes())).andReturn();

		System.out.println("\n 编辑笔记： " + editJavaNoteResult.getResponse().getContentAsString() + "\n");
	}
}
