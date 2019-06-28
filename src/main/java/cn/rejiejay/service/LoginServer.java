package cn.rejiejay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rejiejay.dataaccessobject.User;
import cn.rejiejay.dataaccessobject.UserRepository;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.utils.StringConver;

import com.alibaba.fastjson.JSONObject;

/**
 * 登录逻辑模块
 * 
 * @author _rejeijay
 * @Date 2019年6月10日22:07:04
 */
@Service
public class LoginServer {
	@Autowired
	public UserRepository userRepository;

	/**
	 * 获取密码，并且判断是否正确
	 * 如果正确返回 token
	 */
	public JSONObject verifyPassword(String password) {
		Consequencer consequencer = new Consequencer();
		String realPassword = "";
		String realToken = "";
		User userToken = new User();

		// 根据key值获取密码
		List<User> passwordResult = userRepository.findByKeyname("password");
		System.out.printf("\u001b[31m /login[orm]: " + passwordResult.toString() + "\n"); // 打印 数据库获取的数据
		
		// 判断是否查询到数据
		if (passwordResult.size() > 0) {
			realPassword = passwordResult.get(0).getValue();

		} else {
			consequencer.setMessage("数据库查询为空");
			return consequencer.getJsonObjMessage();
		}

		// 校验密码
		if (realPassword != password) {
			consequencer.setMessage("密码错误(或者账号错误)");
			return consequencer.getJsonObjMessage();
		}
		
		// 根据key值 获取 凭证Token
		List<User> tokenResult = userRepository.findByKeyname("token");

		// 判断是否查询到数据
		if (tokenResult.size() > 0) {
			realToken = tokenResult.get(0).getValue();

		} else {
			// 没有数据创建一个 Token
			userToken.setKeyname("token");
			realToken = StringConver.createRandomStr(42); // token 长度42
			userToken.setValue(realToken);
			
			try {
				userRepository.save(userToken);
				
			} catch (Exception e) {
				consequencer.setMessage("数据库创建token失败！");
				return consequencer.getJsonObjMessage();
			}
		}
		
		consequencer.setResult(1); // 设置为成功
		JSONObject data = new JSONObject();
		data.put("token", realToken);
		consequencer.setData(data); // 返回 token 凭证

		return consequencer.getJsonObjMessage();
	}
}