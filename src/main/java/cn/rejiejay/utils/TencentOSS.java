package cn.rejiejay.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

/**
 * 腾讯云OSS对象云存储方法
 * 
 * 使用 @component注解，将普通要被自动扫描和装配的类实例化到spring容器中。相当于配置文件中的<bean id="" class=""/>
 * 
 * @author _rejeijay
 * @Date 2019-7-16 19:40:30
 */
@Component
public class TencentOSS {
	// 1 初始化用户身份信息（secretId, secretKey）。
	@Value("${rejiejay.tencentoss.secretid}")
	public String secretid;

	@Value("${rejiejay.tencentoss.secretkey}")
	public String secretkey;

	@Value("${rejiejay.tencentoss.region}")
	public String regionconfig;

	@Value("${rejiejay.tencentoss.bucket}")
	public String bucket;

	public COSClient cosClient;

	/**
	 * 构造函数，在_springboot启动的时候会调用下面初始化方法
	 * 
	 * 在默认的构造函数里去获取自动注入的 配置属性，此时Spring还未将该属性注入，因此获取配置文件会报null的错误
	 */
	// public TencentOSS() {
	// }

	/**
	 * @PostConstruct注解表明该方法会在bean初始化后调用
	 */
	@PostConstruct
	private void init() {
		// 这里便可以获取到 _yml配置属性
		COSCredentials cred = new BasicCOSCredentials(secretid, secretkey);
		/**
		 * 2 设置 bucket 的区域, COS 地域的简称请参照
		 * https://cloud.tencent.com/document/product/436/6224 clientConfig 中包含了设置
		 * region, _https(默认 _http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
		 */
		Region region = new Region(regionconfig);
		ClientConfig clientConfig = new ClientConfig(region);
		// 3 生成 _cos 客户端。
		cosClient = new COSClient(cred, clientConfig);
	}
}
