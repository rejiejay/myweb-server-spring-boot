package cn.rejiejay.modular.home.controller;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页测试
 * @author rejeijay
 * @Date 2019年6月10日10:26:14
 */
@RestController
@RequestMapping("/")
public class HomeController {

    @Value("${cn.rejiejay.homeTest}")
    private String homeTest;
    
    /**
	 * 首页Get请求
	 * @return string
	 */
    @RequestMapping("")
    String home() {
        return homeTest + "：Welcome to Rejiejay server side and your place in '/'.";
    }
    
	/**
	 * 首页Post请求
	 * @return string
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String testpost() {
        return homeTest + "：Welcome to Rejiejay server side and your place in '/'.";
	}
}
