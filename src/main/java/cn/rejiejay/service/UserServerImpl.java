package cn.rejiejay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.dataaccessobject.User;
import cn.rejiejay.dataaccessobject.UserRepository;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.utils.StringConver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户逻辑模块 实现类
 * 
 * @author _rejeijay
 * @Date 2019年6月10日22:07:04
 */
@Service
public class UserServerImpl implements UserServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public JSONObject getUser(String username) {
		Consequencer consequencer = new Consequencer();
		User userResult = new User();

		List<User> result = userRepository.findByUsername(username);
		// 判断是否查询到数据
		if (result.size() > 0) {
			userResult = result.get(0);
			JSONObject data = new JSONObject();

			data.put("username", userResult.getUsername());
			data.put("password", userResult.getPassword());
			data.put("token", userResult.getToken());
			data.put("tokenexpired", userResult.getTokenexpired());

			consequencer.setResult(1); // 设置成功
			consequencer.setData(data);
			consequencer.setMessage("successful");

			return consequencer.getJsonObjMessage();
		} else {
			consequencer.setMessage("数据库查询为空");

			return consequencer.getJsonObjMessage();
		}
	}

	@Override
	public JSONObject verifyPassword(String password) {
		Consequencer consequencer = new Consequencer();
		String realPassword = "";
		String realToken = "";
		User userToken = new User();

		// 根据key值获取密码
		List<User> result = userRepository.findByUsername("rejiejay");
		logger.info("UserRepository.findByKeyname(password): " + JSONArray.toJSONString(result)); // 打印 数据库获取的数据

		// 判断是否查询到数据
		if (result.size() > 0) {
			userToken = result.get(0);
			realPassword = userToken.getPassword();
			realToken = userToken.getToken();

		} else {
			consequencer.setMessage("数据库查询为空");
			return consequencer.getJsonObjMessage();
		}

		// 校验密码
		if (!password.equals(realPassword)) {
			consequencer.setMessage("密码错误(或者账号错误)");
			return consequencer.getJsonObjMessage();
		}

		// 判断是否存在token并且判断是否过期
		if (realToken.length() == 0) {
			// 未查询到值就新建一个token
			realToken = StringConver.createRandomStr(42); // token 长度42

			userToken.setToken(realToken);

			try {
				userRepository.save(userToken);

			} catch (Exception e) {
				
				logger.error("UserRepository.save(" + realToken + "): " + e.toString());
				
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
