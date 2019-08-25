package cn.rejiejay.viewobject;

import javax.validation.constraints.NotNull;

/**
 * 新增 记录
 * 
 * @author _rejeijay
 * @Date 2019年8月24日18:43:58
 */
public class AndroidEditRecordReque {
//	@NotNull(message = "androidid cannot be null!")
//	private String androidid;

	private String tag = "";
	
	@NotNull(message = "timestamp cannot be null!")
	private long timestamp;

	@NotNull(message = "fullyear cannot be null!")
	private int fullyear;
	
	@NotNull(message = "month cannot be null!")
	private int month;
	
	@NotNull(message = "week cannot be null!")
	private int week;

	private String imageidentity = "";

	@NotNull(message = "recordtitle cannot be null!")
	private String recordtitle;
	
	private String recordmaterial = "";

	@NotNull(message = "recordcontent cannot be null!")
	private String recordcontent;

//	public String getAndroidid() {
//		return androidid;
//	}
//
//	public void setAndroidid(String androidid) {
//		this.androidid = androidid;
//	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getFullyear() {
		return fullyear;
	}

	public void setFullyear(int fullyear) {
		this.fullyear = fullyear;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
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
		return "AddRecordReque [tag=" + tag + ", timestamp=" + timestamp + ", fullyear=" + fullyear + ", month=" + month
				+ ", week=" + week + ", imageidentity=" + imageidentity + ", recordtitle=" + recordtitle
				+ ", recordmaterial=" + recordmaterial + ", recordcontent=" + recordcontent + "]";
	}

}
