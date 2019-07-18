package cn.rejiejay.scheduled;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时清空文件上传
 * 
 * @author _rejiejay
 * @Date 2019年7月18日20:12:43
 */
@Component
public class CleanUploadeTask {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${rejiejay.file.upload-dir}")
	private String filePath;

	/**
	 * 每天凌晨1点执行清空文件夹
	 */
    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanUploade() {
		File dir = new File(filePath); // 文件夹目录
		
		boolean success = deleteDir(dir);
		
		if (!success) {
			logger.error("清空“" + filePath + "”文件夹失败");
			return;
		}

		dir.mkdir(); // 成功清空文件夹，再次创建目录
    }

	/**
	 * 递归删除指定目录及其子目录下所有文件 File.delete()用于删除“某个文件或者空目录”！所以要删除某个目录及其中的所有文件和子目录，要进行递归删除
	 *
	 * @param dir 将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}
