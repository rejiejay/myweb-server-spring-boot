package cn.rejiejay.viewobject;

import javax.validation.constraints.NotNull;

/**
 * 编辑 JAVA笔记系统 笔记请求实体类
 * 
 * @author _rejeijay
 * @Date 2019年7月16日11:59:09
 */
public class JavaNotesEditReque {
	@NotNull(message = "id cannot be null!")
	private long id;
	
	@NotNull(message = "title cannot be null!")
	private String title;

	private String imageId;

	@NotNull(message = "htmlContent cannot be null!")
	private String htmlContent;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "EditJavaNotesReque [id=" + id + ", title=" + title + ", imageId=" + imageId + ", htmlContent="
				+ htmlContent + ", toString()=" + super.toString() + "]";
	}
}
