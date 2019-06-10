package cn.rejiejay.modular.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页测试
 * @author rejeijay
 * @Date 2019年6月10日10:26:14
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping("")
    String home() {
        return "【home】：Welcome to Rejiejay server side and your place in '/'.";
    }
}
