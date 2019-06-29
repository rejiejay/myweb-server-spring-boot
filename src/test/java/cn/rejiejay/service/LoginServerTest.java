package cn.rejiejay.service;

import cn.rejiejay.service.LoginServerImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by _rejiejay Date 2018/2/2 
 * Description:测试类
 */
public class LoginServerTest extends BaseServiceTests {

	@Autowired
	private LoginServerImpl loginServerImpl;

	// 测试错误密码
	@Test
	public void testMistakePassword() {
		Assert.assertSame("密码错误", 200, loginServerImpl.verifyPassword("1938167"));
	}
}
