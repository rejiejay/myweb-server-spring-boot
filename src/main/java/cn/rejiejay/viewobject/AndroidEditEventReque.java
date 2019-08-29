package cn.rejiejay.viewobject;

import javax.validation.constraints.NotNull;

/**
 * 编辑 记录
 * 
 * @author _rejeijay
 * @Date 2019年8月29日15:32:46
 */
public class AndroidEditEventReque {
	@NotNull(message = "androidid cannot be null!")
	private int androidid;

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

	@NotNull(message = "eventcause cannot be null!")
	private String eventcause;
	
	@NotNull(message = "eventprocess cannot be null!")
	private String eventprocess;
	
	@NotNull(message = "eventresult cannot be null!")
	private String eventresult;
	
	private String eventconclusion = "";

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

	public String getEventcause() {
		return eventcause;
	}

	public void setEventcause(String eventcause) {
		this.eventcause = eventcause;
	}

	public String getEventprocess() {
		return eventprocess;
	}

	public void setEventprocess(String eventprocess) {
		this.eventprocess = eventprocess;
	}

	public String getEventresult() {
		return eventresult;
	}

	public void setEventresult(String eventresult) {
		this.eventresult = eventresult;
	}

	public String getEventconclusion() {
		return eventconclusion;
	}

	public void setEventconclusion(String eventconclusion) {
		this.eventconclusion = eventconclusion;
	}

	@Override
	public String toString() {
		return "AndroidEditEventReque [androidid=" + androidid + ", tag=" + tag + ", timestamp=" + timestamp
				+ ", fullyear=" + fullyear + ", month=" + month + ", week=" + week + ", imageidentity=" + imageidentity
				+ ", eventcause=" + eventcause + ", eventprocess=" + eventprocess + ", eventresult=" + eventresult
				+ ", eventconclusion=" + eventconclusion + "]";
	}
}
