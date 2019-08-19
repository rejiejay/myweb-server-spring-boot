package cn.rejiejay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 安卓端支持（部分支持web端
 * 
 * @author _rejeijay
 * @Date 2019年7月16日10:18:40
 */
@RestController
@RequestMapping("/android")
public class AndroidController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
}
