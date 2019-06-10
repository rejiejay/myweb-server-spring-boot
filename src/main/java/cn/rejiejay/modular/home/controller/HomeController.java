package cn.rejiejay.modular.home.controller;

import org.springframework.beans.factory.annotation.Value;

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

    @Value("${cn.rejiejay.homeTest}")
    private String homeTest;

    @RequestMapping("")
    String home() {
        return homeTest + "：Welcome to Rejiejay server side and your place in '/'.";
    }
}
