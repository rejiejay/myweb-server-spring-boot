package cn.rejiejay.dataaccessobject;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 安卓端 标签
 * 
 * @author _rejeijay
 * @Date 2019年8月26日18:13:19
 */
public interface AndroidRecordEventTagRepository extends CrudRepository<AndroidRecordEventTag, Long> {
	/**
	 * 新增记录
	 */
	@Modifying
	@Transactional
	@Query(value = "insert into android_record_event_tag (tagname) values (?1)", nativeQuery = true)
	int insertRecordEventTag(String tagname);
}
