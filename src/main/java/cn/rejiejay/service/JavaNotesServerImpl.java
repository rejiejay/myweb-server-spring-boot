package cn.rejiejay.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.dataaccessobject.JavaNotes;
import cn.rejiejay.dataaccessobject.JavaNotesRepository;
import cn.rejiejay.utils.Consequencer;

/**
 * 腾讯云对象存储 模块 实现类
 * 
 * @author _rejeijay
 * @Date 2019-7-16 16:11:38
 */
@Service
public class JavaNotesServerImpl implements JavaNotesServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaNotesRepository javaNotesRepository;

	@Value("${rejiejay.tencentoss.origin}")
	private String tencentOssOrigin;

	/**
	 * 创建一个笔记
	 */
	public Consequencer uploadJavaNotes(String title, String imageId, String htmlContent) {
		Consequencer consequent = new Consequencer();

		long timestamp = new Date().getTime();

		logger.debug("执行SQL" + "title:" + title + ";htmlContent:" + htmlContent
				+ ";imageId:" + imageId + ";timestamp:" + timestamp);
		
		int insertNoteResult = javaNotesRepository.insertNote(title, htmlContent, imageId, timestamp);
		

		if (insertNoteResult == 1) {
			JSONObject data = new JSONObject();
			data.put("title", title);
			data.put("htmlContent", htmlContent);
			data.put("imageId", imageId);

			/**
			 * 返回 上传OSS的地址
			 */
			String imageUrl = null;
			if (imageId != null && !imageId.equals("") && !imageId.equals("null")) { // 不为空的情况下
				imageUrl = tencentOssOrigin + "javanotes/" + imageId + ".jpg";
			}
			data.put("imageUrl", imageUrl);
			
			data.put("timestamp", timestamp);
			
			consequent.setSuccess().setData(data);
		} else {
			consequent.setMessage("创建一个笔记SQL执行失败");
		}

		return consequent;
	}

	/**
	 * 获取所有 JAVA Notes 的统计
	 */
	public long getAllNotesCount() {
		return javaNotesRepository.count();
	}

	/**
	 * 根据 页码分页 获取10条 JAVA Notes 
	 */
	public Consequencer getNotesByTime(int pageNo) {
		Consequencer consequent = new Consequencer();
		
		/**
		 * 因为是从零开始的
		 */
		if (pageNo >= 1) {
			pageNo = pageNo - 1;
		}
		
		int startNum = pageNo * 10;
		
		List<JavaNotes> javaNotesResult = javaNotesRepository.findJavaNotesByPageNo(startNum);

		// 这样转换出来的数据是为 [{},{},{}] 空的 是因为 JavaBean出现问题
		// JSONArray array= JSONArray.parseArray(JSON.toJSONString(javaNotesResult));
		// System.out.println("List<JavaNotes> javaNotesResult:" + array.toJSONString());
		
		// 判断是否查询到数据
		if (javaNotesResult.size() <= 0) {
			return consequent.setMessage("查询数据为空！");
		}
		
		// 将 JavaBean 转为 标准JSON
		JSONArray javaNotesArray = new JSONArray();
		javaNotesResult.forEach(javaNote -> {
			JSONObject javaNoteJson = javaNote.toFastJson();
			String imagekey = javaNoteJson.getString("imagekey");
			
			// 这里动个手脚, 封装个imageUrl进去; 主要是为了方便前端。
			String imageUrl = "";
			if (imagekey != null && !imagekey.equals("")) {
				imageUrl = tencentOssOrigin + "javanotes/" + imagekey + ".jpg";
			}
			
			javaNoteJson.put("imageUrl", imageUrl);
			javaNotesArray.add(javaNoteJson);
		});
		
		logger.info("UserRepository.findJavaNotesByPageNo(" + pageNo + "): " + JSONArray.toJSONString(javaNotesArray)); // 打印数据库获取的数据

		// 返回结果
		JSONObject data = new JSONObject();
		data.put("javaNotes", javaNotesArray);
		return consequent.setSuccess(data);
	}
	
	/**
	 * 随机获取N条 JAVA Notes 
	 */
	public Consequencer getNotesByRandom(int total) {
		Consequencer consequent = new Consequencer();
		
		List<JavaNotes> javaNotesResult = javaNotesRepository.findJavaNotesByRandom(total);

		logger.info("javaNotesRepository.findJavaNotesByRandom(" + total + ");" + javaNotesResult);
		
		// 判断是否查询到数据
		if (javaNotesResult.size() <= 0) {
			return consequent.setMessage("查询数据为空！");
		}
		
		// 将 JavaBean 转为 标准JSON
		JSONArray javaNotesArray = new JSONArray();
		javaNotesResult.forEach(javaNote -> {
			JSONObject javaNoteJson = javaNote.toFastJson();
			String imagekey = javaNoteJson.getString("imagekey");
			
			// 这里动个手脚, 封装个imageUrl进去; 主要是为了方便前端。
			String imageUrl = "";
			if (imagekey != null && !imagekey.equals("")) {
				imageUrl = tencentOssOrigin + "javanotes/" + imagekey + ".jpg";
			}
			
			javaNoteJson.put("imageUrl", imageUrl);
			javaNotesArray.add(javaNoteJson);
		});

		// 返回结果
		JSONObject data = new JSONObject();
		data.put("javaNotes", javaNotesArray);
		return consequent.setSuccess(data);
	}
	
	/**
	 * 获取 JAVA Notes 根据 id
	 */
	public Consequencer getNoteById(long id) {
		Consequencer consequent = new Consequencer();
		
		Optional<JavaNotes> JavaNote = javaNotesRepository.findById(id);
		
		JavaNotes oneJavaNote = null;
		
		try {
			oneJavaNote = JavaNote.get();
		} catch (Exception e) {
			return consequent.setMessage("查询数据为空！");
		}
		
		JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(oneJavaNote));
		
		return consequent.setData(data);
	}
}
