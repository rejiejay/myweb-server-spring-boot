package cn.rejiejay.service;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 基础类
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
@Entity
public class User {
	@Id
	private String goodsId;
	
	public User() { }

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
}



