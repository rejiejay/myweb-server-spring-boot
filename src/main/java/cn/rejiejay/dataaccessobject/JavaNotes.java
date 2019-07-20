package cn.rejiejay.dataaccessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * JAVA笔记 实体类
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
@Entity
public class JavaNotes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	@NotNull
	private Long noteid;

	/**
	 * 标题
	 */
	@Column(name = "title")
	@NotNull
	private String title;

	/**
	 * 腾讯云对象存储的基本单元。命名格式为 <ObjectKey>由对象键（ObjectKey）、数据值（Value）、和对象元数据（Metadata）
	 * 对象键（ObjectKey）：对象键是对象在存储桶中的唯一标识。 数据值（Value）：即上传的对象大小。
	 * 对象元数据（Metadata）：是一组名称值对，您可以在上传对象时对其进行设置。 
	 * 
	 * @doc https://cloud.tencent.com/document/product/436/13324
	 */
	@Column(name = "imagekey")
	private String imagekey;
	
	/**
	 * _Html内容
	 */
	@Column(name = "content")
	@NotNull
	private String content;
	
	/**
	 * 用于排序的时间戳
	 */
	@Column(name = "timestamp")
	@NotNull
	private long timestamp;
	
	/**
	 * 以后会用到的分类标签
	 */
	@Column(name = "tag")
	private String tag;

	public JSONObject toFastJson() {
		JSONObject data = new JSONObject();
		data.put("id", this.noteid);
		data.put("title", this.title);
		data.put("imagekey", this.imagekey);
		data.put("content", this.content);
		data.put("timestamp", this.timestamp);
		data.put("tag", this.tag);
		
		return data;
	}
	public Long getNoteid() {
		return noteid;
	}

	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImagekey() {
		return imagekey;
	}

	public void setImagekey(String imagekey) {
		this.imagekey = imagekey;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Override
	public String toString() {
		return "JavaNotes [noteid=" + noteid + ", title=" + title + ", imagekey=" + imagekey + ", content=" + content
				+ ", timestamp=" + timestamp + ", tag=" + tag + "]";
	}
}
