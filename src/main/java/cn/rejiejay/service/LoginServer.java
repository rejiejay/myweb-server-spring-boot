package cn.rejiejay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rejiejay.dataaccessobject.User;
import cn.rejiejay.dataaccessobject.UserRepository;
import cn.rejiejay.utils.Consequencer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 登录逻辑模块
 * 
 * @author _rejeijay
 * @Date 2019年6月10日22:07:04
 */
public class LoginServer  {
	@Autowired
	private UserRepository userRepository;

	/**
	 * 获取密码，并且判断是否正确
	 * 如果正确返回 token
	 */
	public JSONObject verifyPassword(String password) {
		Consequencer consequencer = new Consequencer();
		String realPassword = "";

		// 获取密码
		List<User> result = userRepository.findByKeyname("password");
		System.out.printf("\u001b[31m /login[orm]: " + JSONArray.toJSONString(result) + "\n"); // 打印 数据库获取的数据

		// 判断是否查询到数据
		if (result.size() > 0) {
			realPassword = result.get(0).getValue();

		} else {
			consequencer.setMessage("数据库查询为空");
			return consequencer.getJsonObjMessage();
		}

		// 校验密码
		if (realPassword != password) {
			consequencer.setMessage("密码错误(或者账号错误)");
			return consequencer.getJsonObjMessage();
		}
		
		// 获取 凭证Token
		List<User> token = userRepository.findByKeyname("token");
		

		return consequencer.getJsonObjMessage();
	}
}
