package cn.rejiejay.dataaccessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

/**
 * JAVA笔记 实体类
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

}
