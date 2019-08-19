package cn.rejiejay.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cn.rejiejay.dataaccessobject.AndroidRecordEvents;

/**
 * 安卓端支持（部分支持web端 实体类  的 ORM 接口方法
 * 
 * @author _rejeijay
 * @Date 2019年7月16日16:28:05
 */
public interface AndroidRecordEventRepository extends CrudRepository<AndroidRecordEvents, Long> {
	/**
	 * 时间排序 查询第N页 每页10条
	 */
	@Query(value = "select * from android_record_events order by timestamp desc limit ?1, 10", nativeQuery = true)
	List<AndroidRecordEvents> findRecordEventByPageNo(int pageNo);
}
