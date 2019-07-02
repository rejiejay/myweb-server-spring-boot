package cn.rejiejay.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cn.rejiejay.dataaccessobject.Role;

/**
 * 权限模块 的 ORM 接口方法
 * 
 * @author _rejeijay
 * @Date 2019年6月27日06:37:20
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

	// 根据用户名称 获取用户信息
	@Query(value = "select * from user where username=?1 limit 1", nativeQuery = true)
	List<Role> findByUsername(String username);
	
}
