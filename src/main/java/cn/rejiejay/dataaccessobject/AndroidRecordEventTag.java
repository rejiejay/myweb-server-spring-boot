package cn.rejiejay.dataaccessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 安卓端 标签
 * 
 * @author _rejeijay
 * @Date 2019年8月26日18:13:19
 */
@Entity
public class AndroidRecordEventTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tag_id")
	@NotNull
	private Long tagid;

	@Column(name = "tagname")
	@NotNull
	private String tagname;

	public Long getTagid() {
		return tagid;
	}

	public void setTagid(Long tagid) {
		this.tagid = tagid;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	@Override
	public String toString() {
		return "AndroidRecordEventTag [tagid=" + tagid + ", tagname=" + tagname + "]";
	}
}
