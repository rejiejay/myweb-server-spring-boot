package cn.rejiejay.modular.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import cn.rejiejay.modular.login.controller.User;

/**
 * 登录
 * @author rejeijay
 * @Date 2019年6月10日22:07:04
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    public static class Req {
        private String key;
        private Integer size;
    }
	
	/**
	 * 登录页Post请求登录
	 * consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	 * produces:    指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
     * [这个比较少用到] params： 指定request中必须包含某些参数值是，才让该方法处理。 @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET, params="myParam=myValue")
     * headers： 指定request中必须包含某些指定的header值，才能让该方法处理请求。 @RequestMapping(value = "/pets", method = RequestMethod.GET, headers="Referer=http://www.ifeng.com/")
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public User login(@RequestBody Req req) {
		System.out.printf("111");
		
        User user = new User();
        user.setId(1);
        user.setUsername("zhanghaoliang");
        user.setPassword("1231");
        
        return user;
	}
}
