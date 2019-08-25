package cn.rejiejay.viewobject;

import javax.validation.constraints.NotNull;

/**
 * 编辑 记录
 * 
 * @author _rejeijay
 * @Date 2019年8月24日18:43:58
 */
public class AndroidEditRecordReque {
	@NotNull(message = "androidid cannot be null!")
	private int androidid;

	private String tag = "";

	private String imageidentity = "";

	@NotNull(message = "recordtitle cannot be null!")
	private String recordtitle;
	
	private String recordmaterial = "";

	@NotNull(message = "recordcontent cannot be null!")
	private String recordcontent;

	public int getAndroidid() {
		return androidid;
	}

	public void setAndroidid(int androidid) {
		this.androidid = androidid;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getImageidentity() {
		return imageidentity;
	}

	public void setImageidentity(String imageidentity) {
		this.imageidentity = imageidentity;
	}

	public String getRecordtitle() {
		return recordtitle;
	}

	public void setRecordtitle(String recordtitle) {
		this.recordtitle = recordtitle;
	}

	public String getRecordmaterial() {
		return recordmaterial;
	}

	public void setRecordmaterial(String recordmaterial) {
		this.recordmaterial = recordmaterial;
	}

	public String getRecordcontent() {
		return recordcontent;
	}

	public void setRecordcontent(String recordcontent) {
		this.recordcontent = recordcontent;
	}

	@Override
	public String toString() {
		return "AndroidEditRecordReque [androidid=" + androidid + ", tag=" + tag + ", imageidentity=" + imageidentity
				+ ", recordtitle=" + recordtitle + ", recordmaterial=" + recordmaterial + ", recordcontent="
				+ recordcontent + "]";
	}
}
