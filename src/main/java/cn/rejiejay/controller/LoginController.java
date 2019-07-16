package cn.rejiejay.controller;

// java和 _javax都是Java的API(Application Programming Interface)包，java是核心包，_javax的x是extension的意思，也就是扩展包。
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.service.UserServer;
import cn.rejiejay.utils.DigitalSignature;
import cn.rejiejay.viewobject.AuthorizeReply;
import cn.rejiejay.viewobject.AuthorizeReque;

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
	 * 曾杰杰登录授权 consumes: 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	 * produces: 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回； _params: [这个比较少用到]
	 * 指定request中必须包含某些参数值是，才让该方法处理。 @RequestMapping(value = "/pets/{petId}", method
	 * = RequestMethod.GET, _params="myParam=myValue") headers:
	 * 指定request中必须包含某些指定的header值，才能让该方法处理请求。 @RequestMapping(value = "/pets",
	 * method = RequestMethod.GET, headers="_Referer=http://www.ifeng.com/")
	 */
	@RequestMapping(value = "/rejiejay", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject loginRejiejay(@RequestBody @Valid AuthorizeReque req, BindingResult result) {
		logger.debug("/login/rejiejay[req]: " + JSON.toJSONString(req)); // 打印 请求参数

		if (result.hasErrors()) { // 判断参数是否合法
			for (ObjectError error : result.getAllErrors()) {
				String errorMsg = error.getDefaultMessage();
				logger.warn("/login/rejiejay[req]: " + errorMsg);
				return errorJsonReply(2, errorMsg);
			}
		}

		// 操作ORM获取数据库数据
		JSONObject loginResult = userServer.authorizeRejiejay(req.getPassword());

		logger.debug("/login/rejiejay[orm]: " + loginResult.toString()); // 打印 数据库获取的数据

		// 判断结果是否正确
		if (loginResult.getInteger("result") != 1) { // 不正确的情况下，直接返回错误结果
			return loginResult;
		}

		// 返回响应参数
		JSONObject loginResultData = loginResult.getJSONObject("data");
		return succeedJsonReply(
				new AuthorizeReply(loginResultData.getString("token"), loginResultData.getLong("tokenexpired"))
						.toJSON());
	}

	/**
	 * 刷新曾杰杰token方法
	 */
	@RequestMapping(value = "/refresh/rejiejay", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject refreshRejiejay(@RequestHeader(value = "x-rejiejay-authorization") String authorization,
			@RequestBody @Valid AuthorizeReque req, BindingResult result) {
		logger.debug("/refreshRejiejay[req]: " + JSON.toJSONString(req)); // 打印 请求参数

		if (result.hasErrors()) { // 判断参数是否合法
			for (ObjectError error : result.getAllErrors()) {
				String errorMsg = error.getDefaultMessage();
				logger.warn("/refreshRejiejay[req]: " + errorMsg);
				return errorJsonReply(2, errorMsg);
			}
		}

		// 刷新token和登录不一样，登录是不需要授权的，而刷新token是需要自己确认授权的才给登录
		if (StringUtils.isEmpty(authorization)) {
			return errorJsonReply(40003, "刷新授权失败, 原因: 没有授权"); // 没有授权的意思就是没有检测到token
		}

		// 解析 数字签名
		String digitalSignatureStr = "";
		try {
			// 即使token过期也可以解析出数字签名携带的信息
			JSONObject jsonObjReq = new JSONObject();
			jsonObjReq.put("password", req.getPassword()); // 密码

			digitalSignatureStr = DigitalSignature.DecodeSignature(jsonObjReq.toJSONString(), authorization);
		} catch (Exception e) {
			// 这里有报错的风险，因为这边不是获取标准的body体，是自己生成JSON，所以容易对不上
			return errorJsonReply(40003, "刷新授权失败, 原因: 不合法的凭证类型" + e.toString()); // 直接返回失败
		}

		// 判断解析的JSON是否合法

		logger.warn("/digitalSignatureStr[req]: " + digitalSignatureStr);
		if (!JSON.isValid(digitalSignatureStr)) { // 合法返回 _ture 不合法返回false
			return errorJsonReply(40003, "刷新授权失败, 原因: 不合法的凭证类型");
		}

		// 合法的情况下 获取解析后的数字签名里面携带的 JSON 数据
		JSONObject digitalSignature = JSON.parseObject(digitalSignatureStr);

		// 判断该用户名称即可
		if (!digitalSignature.getString("username").equals("rejiejay")) {
			return errorJsonReply(40003, "刷新授权失败, 原因: 不合法的凭证类型"); // 用户名不是 _rejiejay都是不合法
		}

		/**
		 * 操作ORM刷新新的token。这里的逻辑和登录授权是一模一样的
		 */
		JSONObject refreshResult = userServer.authorizeRejiejay(req.getPassword());

		logger.debug("/login/refresh/rejiejay[orm]: " + refreshResult.toString()); // 打印 数据库获取的数据

		// 判断结果是否正确
		if (refreshResult.getInteger("result") != 1) { // 不正确的情况下，直接返回错误结果
			return refreshResult;
		}

		// 返回响应参数
		JSONObject refreshResultData = refreshResult.getJSONObject("data");
		return succeedJsonReply(
				new AuthorizeReply(refreshResultData.getString("token"), refreshResultData.getLong("tokenexpired"))
						.toJSON());
	}
}
