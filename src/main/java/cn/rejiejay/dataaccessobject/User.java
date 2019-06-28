package cn.rejiejay.dataaccessobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户模块 实体类
 * 因为只有一个用户 所以此模块使用key value 模式
 * 会存很多东西，但是基本都是与用户相关，现阶段有：
 * password
 * token
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
    private String keyname;
    private String value;

    public User() {}

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}



