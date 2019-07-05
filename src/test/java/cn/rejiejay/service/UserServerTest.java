package cn.rejiejay.service;

import cn.rejiejay.service.UserServerImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by _rejiejay Date 2019年7月5日16:51:27
 * Description:测试类
 */
public class UserServerTest extends BaseServiceTests {

	@Autowired
	private UserServerImpl userServerImpl;

	// 测试获取不存在的用户
	@Test
	public void testGetUserInfo() {
		Assert.assertSame("不存在此用户用户", 200, userServerImpl.getUserInfo("zhoujielun"));
	}

	// 测试曾杰杰登录
	@Test
	public void testLoginRejiejay() {
		Assert.assertSame("曾杰杰登录成功", 200, userServerImpl.loginByRejiejay("1938167"));
	}
}
