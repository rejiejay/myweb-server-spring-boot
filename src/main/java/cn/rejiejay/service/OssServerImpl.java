package cn.rejiejay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
public class OssServerImpl implements OssServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	final Base64.Decoder decoder = Base64.getDecoder();

	@Autowired
	private TencentOSS tencentOSS;

	@Value("${rejiejay.file.upload-dir}")
	private String filePath;

	/**
	 * 上传到JAVA笔记系统的图片
	 */
	public Consequencer uploadJavaNotesImage(String imageId) {
		return uploadGeneralImage("javanotes", imageId, true);
	}

	/**
	 * 判断是否存在此图片
	 */
	public Consequencer isExistsJavaNotesImage(String imageId) {
		return isExistsImage("javanotes", imageId, true);
	}

	/**
	 * 删除JAVA笔记系统的图片 根据 imageId
	 */
	public Consequencer delJavaNotesImage(String imageId) {
		return delGeneralImage("javanotes", imageId, true);
	}

	/**
	 * 上传到Android的图片
	 */
	public Consequencer uploadAndroidImage(String imageId) {
		return uploadGeneralImage("android", imageId, false);
	}

	/**
	 * 删除Android的图片 根据 imageId
	 */
	public Consequencer delAndroidImage(String imageId) {
		return delGeneralImage("android", imageId, false);
	}

	/**
	 * 通用上传方法
	 */
	public Consequencer uploadGeneralImage(String position, String imageId, Boolean isJPG) {
		Consequencer consequent = new Consequencer();

		/**
		 * 先判断是否存在图片id
		 */
		File file = new File(filePath + imageId);
		if (!file.exists()) {
			// 文件不存在的情况下
			return consequent.setMessage("文件id“" + imageId + "”是错误的！请检查、");
		}

		String imageStr = ""; // 读取文件夹里面的内容

		try {
			FileInputStream in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完

			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			imageStr = new String(buffer, "UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
			return consequent.setMessage("读取本地图片流失败，原因：" + e.toString());
		}

		// 线程池大小，建议在客户端与 COS 网络充足(如使用腾讯云的 CVM，同地域上传 COS)的情况下，设置成16或32即可, 可较充分的利用网络资源
		// 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
		ExecutorService threadPool = Executors.newFixedThreadPool(16); // 我一般都是网络充足的
		// 传入一个 _threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
		TransferManager transferManager = new TransferManager(tencentOSS.cosClient, threadPool);

		// 对象键（Key）是对象在存储桶中的唯一标识。这里使用时间戳即可

		String suffix = isJPG ? ".jpg" : ".png";
		String key = "myserver/" + position + "/" + imageId + suffix;

		/**
		 * 解码
		 * 
		 * 特别注意： The part "data:image/_png;base64," does not belong to the real Base64
		 * data.
		 */
		byte[] decoderimg = decoder.decode(imageStr.replaceFirst("^.*,", "").trim());

		// 获取指定文件的输入流
		InputStream input = new ByteArrayInputStream(decoderimg);

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		// 必须设置ContentLength
		meta.setContentLength(decoderimg.length);

		PutObjectRequest putObjectRequest = new PutObjectRequest(tencentOSS.bucket, key, input, meta);

		// 文件上传
		Upload upload = transferManager.upload(putObjectRequest);

		// 等待传输结束
		try {
			upload.waitForCompletion();
		} catch (CosClientException | InterruptedException e) {
			transferManager.shutdownNow(); // 记得关闭 TransferManger

			e.printStackTrace();
			return consequent.setMessage("上传本地图片到OSS失败，原因：" + e.toString());
		}

		transferManager.shutdownNow(); // 记得关闭 TransferManger
		return consequent.setSuccess();
	}

	/**
	 * 判断是否存在此图片
	 */
	public Consequencer isExistsImage(String position, String imageId, Boolean isJPG) {
		Consequencer consequent = new Consequencer();

		// 对象键（Key）是对象在存储桶中的唯一标识。
		String suffix = isJPG ? ".jpg" : ".png";
		String key = "myserver/" + position + "/" + imageId + suffix;

		logger.info("查询存储桶中是否存在指定的对象tencentOSS.cosClient.getObjectMetadata(" + tencentOSS.bucket + ", " + key + ")"); // 打印数据库获取的数据

		try {
			tencentOSS.cosClient.getObjectMetadata(tencentOSS.bucket, key);
		} catch (CosClientException e) {
			return consequent.setMessage("查询不存在对象:" + e.toString());
		}

		return consequent.setSuccess();
	}

	/**
	 * 通用删除方法
	 */
	public Consequencer delGeneralImage(String position, String imageId, Boolean isJPG) {
		Consequencer consequent = new Consequencer();

		/**
		 * 查询存储桶中是否存在指定的对象
		 */
		String suffix = isJPG ? ".jpg" : ".png";
		String key = "myserver/" + position + "/" + imageId + suffix;
		Consequencer imageExists = isExistsImage(position, imageId, isJPG);

		if (imageExists.getResult() != 1) {
			return consequent.setSuccess(); // 不存在这张图片 表示删除成功？
		}

		/**
		 * 开始删除（这里是存在图片的情况
		 */
		try {
			logger.info("始删除存储桶中指定的对象" + tencentOSS.bucket + key); // 打印数据库获取的数据
			tencentOSS.cosClient.deleteObject(tencentOSS.bucket, key);
		} catch (CosClientException e) {
			return consequent.setMessage("删除图片" + key + "失败，原因：" + e.toString());
		}

		return consequent.setSuccess();

	}
}
