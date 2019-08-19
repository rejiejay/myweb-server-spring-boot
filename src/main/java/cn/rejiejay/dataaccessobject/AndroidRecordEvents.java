package cn.rejiejay.dataaccessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 安卓端支持（部分支持web端 实体类
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
@Entity
public class AndroidRecordEvents {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "android_id")
	@NotNull
	private Long androidid;

	/**
	 * 类型
	 * 2种类型
	 * record 记录类型
	 * event 事件类型
	 */
	@Column(name = "type")
	@NotNull
	private String type;
	
	/**
	 * 图片 腾讯云对象存储的基本单元。 <ObjectKey>
	 * @doc https://cloud.tencent.com/document/product/436/13324
	 */
	@Column(name = "imageidentity")
	private String imageidentity;
	
	/**
	 * 记录标题
	 * record 类型独有
	 */
	@Column(name = "recordtitle")
	private String recordtitle;
	
	/**
	 * 素材-思路-联想
	 * record 类型独有
	 */
	@Column(name = "recordmaterial")
	private String recordmaterial;
	
	/**
	 * 记录内容
	 * record 类型独有
	 */
	@Column(name = "recordcontent")
	private String recordcontent;
	
	/**
	 * 标签
	 * record 类型独有
	 */
	@Column(name = "tag")
	private String tag;
	
	/**
	 * 事件起因
	 * event 类型独有
	 */
	@Column(name = "eventcause")
	private String eventcause;
	
	/**
	 * 事件过程
	 * event 类型独有
	 */
	@Column(name = "eventprocess")
	private String eventprocess;
	
	/**
	 * 事件结果
	 * event 类型独有
	 */
	@Column(name = "eventresult")
	private String eventresult;
	
	/**
	 * 事件结论
	 * event 类型独有
	 */
	@Column(name = "eventconclusion")
	private String eventconclusion;

	/**
	 * 用于排序的时间戳
	 */
	@Column(name = "timestamp")
	@NotNull
	private long timestamp;
	
	/**
	 * 用于方便统计的 年份（手动update进去
	 * 2019
	 */
	@Column(name = "fullyear")
	@NotNull
	private long fullyear;
	
	/**
	 * 用于方便统计的 月份（手动update进去
	 * 1~12
	 */
	@Column(name = "month")
	@NotNull
	private int month;
	
	/**
	 * 用于方便统计的 星期（手动update进去
	 * 1~7 week1
	 * 7~14 week2
	 * 14~21 week3
	 * 21——31 week4
	 */
	@Column(name = "week")
	@NotNull
	private int week;

	public Long getAndroidid() {
		return androidid;
	}

	public void setAndroidid(Long androidid) {
		this.androidid = androidid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getFullyear() {
		return fullyear;
	}

	public void setFullyear(long fullyear) {
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

	@Override
	public String toString() {
		return "AndroidRecordEvents [androidid=" + androidid + ", type=" + type + ", imageidentity=" + imageidentity
				+ ", recordtitle=" + recordtitle + ", recordmaterial=" + recordmaterial + ", recordcontent="
				+ recordcontent + ", tag=" + tag + ", eventcause=" + eventcause + ", eventprocess=" + eventprocess
				+ ", eventresult=" + eventresult + ", eventconclusion=" + eventconclusion + ", timestamp=" + timestamp
				+ ", fullyear=" + fullyear + ", month=" + month + ", week=" + week + "]";
	}
	
}
