package cn.rejiejay.utils;

import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 字符串工具类方法
 * 
 * @author _rejeijay
 * @Date 2019年6月10日10:26:14
 */
public class StringConver {

	/**
	 * 根据长度创建随机字符串
	 */
	public static String createRandomStr(int length) {
		String randomString = "";

		String stringArray = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, \"a\", \"b\", \"c\", \"d\", \"e\", \"f\", \"g\", \"h\", \"i\", \"j\", \"k\", \"l\", \"m\", \"n\", \"o\", \"p\", \"q\", \"r\", \"s\", \"t\", \"u\", \"v\", \"w\", \"x\", \"y\", \"z\", \"A\", \"B\", \"C\", \"D\", \"E\", \"F\", \"G\", \"H\", \"I\", \"J\", \"K\", \"L\", \"M\", \"N\", \"O\", \"P\", \"Q\", \"R\", \"S\", \"T\", \"U\", \"V\", \"W\", \"X\", \"Y\", \"Z\", \"-\", \"_\"]";
 
		List<?> JSONArray = JSON.parseArray(stringArray);
		Collections.shuffle(JSONArray); // 打乱

		if (length <= 0) { // 长度不能小于0
			return "??????";
		}

		// 根据长度创建
		for (int i = 0; i < length; i++) {
			randomString += JSONArray.get(new java.util.Random().nextInt(JSONArray.size() - 1)).toString();
		}

		return randomString;
	}
}
