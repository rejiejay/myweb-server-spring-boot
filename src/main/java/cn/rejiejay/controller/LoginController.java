package cn.rejiejay.controller;

import java.util.List;

// java和 _javax都是Java的API(Application Programming Interface)包，java是核心包，_javax的x是extension的意思，也就是扩展包。
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.service.LoginServer;
import cn.rejiejay.service.UserServer;
import cn.rejiejay.viewobject.LoginReply;
import cn.rejiejay.viewobject.LoginReque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录
 * 
 * @author _rejeijay
 * @Date 2019年6月10日22:07:04
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 虽然注入dao层也可以直接调用到数据库层。但是按照规范一般不这么做。 一般注入的是service接口，然后通过service.xxx()方法。
	 * 这么做的原因： 1. 因为安全，因为别人无法通过反编译获取到你的具体实现代码。 2. 让代码更具有可读性。
	 */
	@Autowired
	private UserServer userServer;

	/**
	 * 曾杰杰登录授权
	 * consumes: 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	 * produces: 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
     * _params: [这个比较少用到] 指定request中必须包含某些参数值是，才让该方法处理。 @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET, params="myParam=myValue")
     * headers: 指定request中必须包含某些指定的header值，才能让该方法处理请求。 @RequestMapping(value = "/pets", method = RequestMethod.GET, headers="Referer=http://www.ifeng.com/")
	 */
	@RequestMapping(value = "/rejiejay", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject login(@RequestBody @Valid LoginReque req, BindingResult result) {
		logger.debug("/login[req]: " + JSON.toJSONString(req)); // 打印 请求参数

		if (result.hasErrors()) { // 判断参数是否合法
			for (ObjectError error : result.getAllErrors()) {
				String errorMsg = error.getDefaultMessage();
				logger.warn("/login[req]: " + errorMsg);
				return errorJsonReply(2, errorMsg);
			}
		}

		// 操作ORM获取数据库数据
		JSONObject loginResult = userServer.loginByRejiejay(req.getPassword());

		logger.debug("/login[orm]: " + loginResult.toString()); // 打印 数据库获取的数据

		// 判断结果是否正确
		if (loginResult.getInteger("result") != 1) { // 不正确的情况下，直接返回错误结果
			return loginResult;
		}
		
		// 返回响应参数
		LoginReply userToken = new LoginReply(loginResult.getJSONObject("data").getString("token"));
		JSONObject replyJson = userToken.toJSON();

		return succeedJsonReply(replyJson);

	}
}
