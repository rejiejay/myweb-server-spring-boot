package cn.rejiejay.service;

import java.util.Date;
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
 * 用户模块 实现类
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
	public JSONObject securityVerifiByDigitalSign(String authorizeName, String licensedRole, String accessToken) {
		Consequencer consequencer = new Consequencer();
		User dbUserResult = new User();
		
		// 根据 需要授权的用户名 查询用户信息
		List<User> dbUserResultList = userRepository.findByUsername(authorizeName);
		logger.info("UserRepository.findByUsername(" + authorizeName + "): " + JSONArray.toJSONString(dbUserResultList)); // 打印 数据库获取的数据
		
		// 判断是否查询到数据
		if (dbUserResultList.size() > 0) {
			dbUserResult = dbUserResultList.get(0);

		} else {
			// 未查询到数据表明没有此用户，说明token凭证是错误的
			consequencer.setResult(40003).setMessage("无效的凭证类型");
			return consequencer.getJsonObjMessage();
		}
		
		// 优先判断 前端携带的凭证 是否正确
		if (!licensedRole.equals(dbUserResult.getToken())) {
			consequencer.setResult(40003).setMessage("无效的凭证类型");
			return consequencer.getJsonObjMessage();
		}

		// 前端携带的凭证 是正确的情况下判断是否过期
		if (new Date().getTime() >= dbUserResult.getTokenexpired()) {
			consequencer.setResult(40004).setMessage("您的凭证已过期");
			return consequencer.getJsonObjMessage();
		}

		// 判断 拦截器允许的角色 是否和 数据库的角色相对应
		if (!licensedRole.equals(dbUserResult.getRole())) {
			// 和数据库查询的角色对应失败， 表示token是有效，但是没有相应的权限
			consequencer.setResult(40005).setMessage("您没有相应的权限");
			return consequencer.getJsonObjMessage();
			
		}
		
		// 表示 存在此用户 并且 是经过授权（凭证正确 并且具有相应角色权限
		consequencer.setSuccess();
		return consequencer.getJsonObjMessage();
	}
}
