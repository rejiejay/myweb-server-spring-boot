package cn.rejiejay.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cn.rejiejay.dataaccessobject.User;

/**
 * 用户模块 的 ORM 接口方法
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
public interface UserRepository extends CrudRepository<User, Long> {

	// 通过key名称获取值
	@Query(value = "select * from user where keyname=?1 limit 1", nativeQuery = true)
	List<User> findByKeyname(String keyname);

	// 创建 Token
	@Query(value = "insert into user (keyname, value) values (\"Token\", ?1)", nativeQuery = true)
	int saveToken(String token);
}
