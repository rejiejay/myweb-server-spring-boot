package cn.rejiejay.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.security.SecurityAnnotater;

/**
 * 主页测试
 * 
 * @author _rejeijay
 * @Date 2019年6月10日10:26:14
 */
@RestController
@RequestMapping("/")
public class HomeController {

	@Value("${rejiejay.homeTest}")
	private String homeTest;

	/**
	 * 首页Get请求
	 * RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
	 * 
	 * @return string
	 */
	@RequestMapping("/")
	public String home() {
		return homeTest + "：Welcome to Rejiejay server side and your place in '/'.";
	}

	/**
	 * 首页Post请求 value： 指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）； method：
	 * 指定请求的method类型， GET、POST、PUT、DELETE等；
	 * 
	 * @return string
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String testpost() {
		return homeTest + "：Welcome to Rejiejay server side and your place in '/'.";
	}

	@SecurityAnnotater(role = "admin")
	@RequestMapping("/security")
	public String getsecurity() {
		return "securityGet";
	}

	@SecurityAnnotater(role = "admin")
	@RequestMapping(value = "/security", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
	public String postsecurity(@RequestBody JSONObject req, BindingResult result) {
		return "securityPost";
	}
}
