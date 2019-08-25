package cn.rejiejay.viewobject;

import javax.validation.constraints.NotNull;

/**
 * 删除 记录
 * 
 * @author _rejeijay
 * @Date 2019年8月24日18:43:58
 */
public class AndroidDelRecordReque {
	@NotNull(message = "androidid cannot be null!")
	private int androidid;

	public int getAndroidid() {
		return androidid;
	}

	public void setAndroidid(int androidid) {
		this.androidid = androidid;
	}

	@Override
	public String toString() {
		return "DelRecordReque [androidid=" + String.valueOf(androidid) + "]";
	}
}
