package cn.rejiejay.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.utils.TencentOSS;

/**
 * 腾讯云对象存储 模块 实现类
 * 
 * @author _rejeijay
 * @Date 2019-7-16 16:11:38
 */
@Service
public class JavaNotesServerImpl implements JavaNotesServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TencentOSS tencentOSS;

	/**
	 * 上传到JAVA笔记系统的图片
	 */
	public Consequencer uploadJavaNotesImage(String imgBase64, String imgName) {
		Consequencer consequent = new Consequencer();

		// 线程池大小，建议在客户端与 COS 网络充足(如使用腾讯云的 CVM，同地域上传 COS)的情况下，设置成16或32即可, 可较充分的利用网络资源
		// 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
		ExecutorService threadPool = Executors.newFixedThreadPool(16); // 我一般都是网络充足的
		// 传入一个 _threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
		TransferManager transferManager = new TransferManager(tencentOSS.cosClient, threadPool);

		// 对象键（Key）是对象在存储桶中的唯一标识。这里使用时间戳即可
		String key = "myserver/javanotes/" + imgName + ".jpg";

		// 获取指定文件的输入流
		InputStream input;
		try {
			input = new ByteArrayInputStream(imgBase64.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException error) {
			transferManager.shutdownNow(); // 记得关闭 TransferManger
			consequent.setMessage(error.toString());
			error.printStackTrace();
			return consequent;
		}

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		// 必须设置ContentLength
		meta.setContentLength(imgBase64.length());

		PutObjectRequest putObjectRequest = new PutObjectRequest(tencentOSS.bucket, key, input, meta);

		// 本地文件上传
		Upload upload = transferManager.upload(putObjectRequest);

		// 等待传输结束
		try {
			upload.waitForCompletion();
		} catch (CosClientException | InterruptedException e) {
			transferManager.shutdownNow(); // 记得关闭 TransferManger
			consequent.setMessage(e.toString());
			e.printStackTrace();
			return consequent;
		}

		consequent.setSuccess();
		transferManager.shutdownNow(); // 记得关闭 TransferManger

		return consequent;
	}
}
