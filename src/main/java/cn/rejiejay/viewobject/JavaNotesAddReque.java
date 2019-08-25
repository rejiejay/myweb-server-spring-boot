package cn.rejiejay.viewobject;

import javax.validation.constraints.NotNull;

/**
 * 新增JAVA笔记系统 笔记请求实体类
 * 
 * @author _rejeijay
 * @Date 2019年7月16日11:59:09
 */
public class JavaNotesAddReque {
	@NotNull(message = "title cannot be null!")
	private String title;

	private String imageId;

	@NotNull(message = "htmlContent cannot be null!")
	private String htmlContent;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
}
