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

	@NotNull(message = "eventtitle cannot be null!")
	private String eventtitle;
	
	@NotNull(message = "eventsituation cannot be null!")
	private String eventsituation;
	
	@NotNull(message = "eventtarget cannot be null!")
	private String eventtarget;
	
	@NotNull(message = "eventaction cannot be null!")
	private String eventaction;
	
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

	public String getEventtitle() {
		return eventtitle;
	}

	public void setEventtitle(String eventtitle) {
		this.eventtitle = eventtitle;
	}

	public String getEventsituation() {
		return eventsituation;
	}

	public void setEventsituation(String eventsituation) {
		this.eventsituation = eventsituation;
	}

	public String getEventtarget() {
		return eventtarget;
	}

	public void setEventtarget(String eventtarget) {
		this.eventtarget = eventtarget;
	}

	public String getEventaction() {
		return eventaction;
	}

	public void setEventaction(String eventaction) {
		this.eventaction = eventaction;
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
				+ ", eventtitle=" + eventtitle + ", eventsituation=" + eventsituation + ", eventtarget=" + eventtarget
				+ ", eventaction=" + eventaction + ", eventresult=" + eventresult + ", eventconclusion="
				+ eventconclusion + "]";
	}
}
