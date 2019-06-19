package cn.rejiejay.controller;

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
	 * RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
	 * @return string
	 */
    @RequestMapping("")
    String home() {
        return homeTest + "：Welcome to Rejiejay server side and your place in '/'.";
    }
    
	/**
	 * 首页Post请求
	 * value：     指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）；
	 * method：  指定请求的method类型， GET、POST、PUT、DELETE等；
	 * @return string
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String testpost() {
        return homeTest + "：Welcome to Rejiejay server side and your place in '/'.";
	}
}
