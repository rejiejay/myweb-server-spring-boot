package cn.rejiejay.utils;

import org.junit.Test;

/**
 * Created by _rejiejay Date 2019年7月4日08:06:09 Description:测试类
 */
public class digitalSignatureTest extends BaseUtilsTests {
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
			String reqParam = "username=rejiejay&password=DFqew1938167";
			String username = "rejiejay";
			String token = "c4ca4238a0b923820dcc509a6f75849b";
			testResults = DigitalSignature.EncryptSignature(reqParam, username, token); // 7KvKmB6K2d400lnbBnU4y24cIBzqWWZJ3x8LdHVSS8Dow2gCcdHOMt0EMGGoT3TVaElRhBGVE0eJctihZJl33O3tXCVdP3QVQ+OKQ2QdSZolzW2LvDZyxp3VagejXg/6

		} catch (Exception e) {
			testResults = "error: " + e.toString();
		}
		System.out.println(testResults + "\n");
	}

	// 测试数字签名解密方法
	@Test
	public void DecodeSignatureTest() {
		String testResults = "ready";
		try {
			String reqParam = "username=rejiejay&password=DFqew1938167";
			String digitalSignatureEncodedString = "7KvKmB6K2d400lnbBnU4y24cIBzqWWZJ3x8LdHVSS8Dow2gCcdHOMt0EMGGoT3TVaElRhBGVE0eJctihZJl33O3tXCVdP3QVQ+OKQ2QdSZolzW2LvDZyxp3VagejXg/6";
			testResults = DigitalSignature.DecodeSignature(reqParam, digitalSignatureEncodedString); // {"username":"rejiejay","token":"c4ca4238a0b923820dcc509a6f75849b"}

		} catch (Exception e) {
			testResults = "error: " + e.toString();
		}
		System.out.println(testResults + "\n");
	}
}
