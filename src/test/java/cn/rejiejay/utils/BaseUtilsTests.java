package cn.rejiejay.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseUtilsTests {
	@Before
	public void init() {
		System.out.println("\u001B[31m -----------------开始测试----------------- \n");
	}

	@After
	public void after() {
		System.out.println("\u001B[31m -----------------测试结束----------------- \n \u001B[0m");
	}
}
