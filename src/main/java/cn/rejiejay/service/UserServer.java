package cn.rejiejay.service;

import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.utils.Consequencer;

/**
 * 用户模块模块
 * 
 * 【规范】先说明一下为什么要service层和dao层分开
 * 
 * 首先解释面上意思，service是业务层，dao是数据访问层。
 * 1.DAO层一定是和数据库的每张表一一对应，而service则不是。dao的作用是操作数据库底层，不做任何业务逻辑代码
 * 
 * 2.service是业务层（实现业务逻辑的地方），凡是业务逻辑代码都写在这。一个接口对应一个实现类 例如: 接口类 LoginServer 必须有一个
 * LoginServerImpl implements LoginServer
 * 
 * 3.所以整个项目的调用顺序是 Controller -> service -> dao
 * 
 * @author _admin
 * @Date 2019年6月28日16:01:14
 */
public interface UserServer {
	/**
	 * 曾杰杰 登录授权
	 */
	public Consequencer authorizeRejiejay(String password);

	/**
	 * 通过数字签名携带的信息 验证是否符合权限
	 * @param authorizeName 需要授权的用户名
	 * @param licensedRole 拦截器允许的角色
	 * @param accessToken 前端携带的凭证
	 * @return
	 */
	public JSONObject securityVerifiByDigitalSign(String authorizeName, String licensedRole, String accessToken);

}
