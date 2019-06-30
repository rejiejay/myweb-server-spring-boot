package cn.rejiejay.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户逻辑模块
 * 
 * 【规范】先说明一下为什么要service层和dao层分开
 * 
 * 首先解释面上意思，service是业务层，dao是数据访问层。
 * 1.DAO层一定是和数据库的每张表一一对应，而service则不是。dao的作用是操作数据库底层，不做任何业务逻辑代码
 * 
 * 2.service是业务层（实现业务逻辑的地方），凡是业务逻辑代码都写在这。一个接口对应一个实现类 
 *   例如: 接口类 UserServer 必须有一个 UserServerImpl implements UserServer
 *   
 * 3.所以整个项目的调用顺序是 Controller -> service -> dao
 * 
 * @author _admin
 * @Date 2019年6月28日16:01:14
 */
public interface UserServer {
	/**
	 * 获取数据库密码，并且判断是否正确
	 * 如果正确返回 token
	 */
	public JSONObject verifyPassword(String password);

	/**
	 * 获取用户信息
	 */
	public JSONObject getUser(String username);
}
