package cn.rejiejay.controller;

import cn.rejiejay.controller.BaseController;
import cn.rejiejay.dataaccessobject.User;
import cn.rejiejay.dataaccessobject.UserRepository;
import cn.rejiejay.viewobject.LoginReque;
import cn.rejiejay.viewobject.LoginReply;
import cn.rejiejay.service.LoginServer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// java和 _javax都是Java的API(Application Programming Interface)包，java是核心包，_javax的x是extension的意思，也就是扩展包。
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 登录
 * 
 * @author _rejeijay
 * @Date 2019年6月10日22:07:04
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
	@Autowired
	public LoginServer loginServer;
	
	/**
	 * 登录页Post请求登录
	 * consumes: 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	 * produces: 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
     * _params: [这个比较少用到] 指定request中必须包含某些参数值是，才让该方法处理。 @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET, params="myParam=myValue")
     * headers: 指定request中必须包含某些指定的header值，才能让该方法处理请求。 @RequestMapping(value = "/pets", method = RequestMethod.GET, headers="Referer=http://www.ifeng.com/")
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public JSONObject login(@RequestBody @Valid LoginReque req, BindingResult result) {

		System.out.printf("\u001b[31m /login[req]: " + JSON.toJSONString(req) + "\n"); // 打印 请求参数

		if (result.hasErrors()) { // 判断参数是否合法
			for (ObjectError error : result.getAllErrors()) {
				String errorMsg = error.getDefaultMessage();
				System.out.println("\u001b[31m /login[error]: " + errorMsg + "\n");
				return errorJsonReply(2, errorMsg);
			}
		}

		// 操作ORM获取数据库数据
		JSONObject verifyResult = loginServer.verifyPassword(req.getPassword());

		System.out.printf("\u001b[31m /login[rep]: " + JSON.toJSONString(verifyResult) + "\n"); // 打印 响应参数
		return succeedJsonReply(verifyResult);
	}
}
