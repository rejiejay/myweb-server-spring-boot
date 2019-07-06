package cn.rejiejay.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.rejiejay.dataaccessobject.User;

/**
 * 用户模块 的 ORM 接口方法
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
public interface UserRepository extends CrudRepository<User, Long> {
	// 根据id刷新token
	@Modifying
	@Transactional
	@Query(value = "update user set token=?1, tokenexpired=?2  where user_id=?3", nativeQuery = true)
	int refreshTokenByUserId(String token, Long tokenexpired, Long Id);

	// 通过key名称获取值
	@Query(value = "select * from user where username=?1 limit 1", nativeQuery = true)
	List<User> findByUsername(String username);

	/**
	 * 创建 Token （这个方法暂时还没用上 放在这里作为参考一下
	 * 
	 * @param token
	 * @return
	 */
	@Modifying
	@Query(value = "insert into user (keyname, value) values (\"token\", ?1)", nativeQuery = true)
	int saveToken(String token);
}
