package cn.rejiejay.service;

import cn.rejiejay.TmallApplicationTests;
import cn.rejiejay.service.LoginServerImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by _rejiejay Date 2018/2/2 
 * Description:测试类
 */
public class LoginServerTest extends TmallApplicationTests {

	@Autowired
	private LoginServerImpl loginServerImpl;

	@Test
	public void testVerifyPassword() {
		Assert.assertSame("密码错误", 200, loginServerImpl.verifyPassword("1938167"));
	}
}
