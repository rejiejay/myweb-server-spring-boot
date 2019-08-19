package cn.rejiejay.utils;

import org.junit.Test;

/**
 * Created by _rejiejay Date 2019年7月4日08:06:09 Description:测试类
 */
public class DigitalSignatureTest extends BaseUtilsTests {
	// 测试MD5加密算法
	@Test
	public void ReqParamToMD5Test() {
		String testResults = "ready";
		try {
			testResults = DigitalSignature.ReqParamToMD5("1"); // c4ca4238a0b923820dcc509a6f75849b
		} catch (Exception e) {
			testResults = "error: " + e.toString();
		}
		System.out.println(testResults + "\n");
		// System.out.println(testResults.substring(testResults.length() - 16) + "\n");
		// // 裁剪后16位
	}

	// 测试数字签名加密方法
	@Test
	public void EncryptSignatureTest() {
		String testResults = "ready";
		try {
			String reqParam = "{\"password\":\"1938167\"}";
			String username = "rejiejay";
			String token = "096lLFmeJemGSW62x3k-z6nlagx_BgmhkcEkEdT30i";
			testResults = DigitalSignature.EncryptSignature(reqParam, username, token);

		} catch (Exception e) {
			testResults = "error: " + e.toString();
		}
		
		// h01LkvMB9oZbBpa+XxZcVr2Fj4KlJ6lcd8KtjKMBKaH7mw6A2b6tTFe5qZyBZUi7Es4fo58aOO79jXS4MgeiCykNkDQQurj7NhYQYTJED4Y=
		System.out.println(testResults + "\n");
	}

	// 测试数字签名解密方法
	@Test
	public void DecodeSignatureTest() {
		String testResults = "ready";
		try {
			String reqParam = "{\"id\":4,\"title\":\"这是测试Edit标题\",\"imageId\":\"\",\"htmlContent\":\"这是测试Edit内容\"}";
			String digitalSignatureEncodedString = "v5y40a5Pitu5nP2JqZwwYV4wJ0wbvCQEqrH09RIAxj1B0VZKxpiziBIaArgdb6MZ6p8XE8Gk4FSGo8x8Fq2EJB3etP7VqDQDEWeTe5BqJbc=";

			testResults = DigitalSignature.DecodeSignature(reqParam, digitalSignatureEncodedString);

		} catch (Exception e) {
			testResults = "error: " + e.toString();
		}
		System.out.println(testResults + "\n");
	}
}
