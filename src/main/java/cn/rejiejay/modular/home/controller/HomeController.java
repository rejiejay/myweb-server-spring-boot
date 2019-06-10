package cn.rejiejay.modular.home.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页测试
 * @author rejeijay
 * @Date 2019年6月10日10:26:14
 */
@RestController
@RequestMapping("/")
public class HomeController {

    @RequestMapping("")
    String home() {
        return "【home】：Welcome to Rejiejay server side and your place in '/'.";
    }
}
