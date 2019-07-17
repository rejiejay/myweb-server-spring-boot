package cn.rejiejay.dataaccessobject;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * JAVA笔记模块 的 ORM 接口方法
 * 
 * @author _rejeijay
 * @Date 2019年7月16日16:28:05
 */
public interface JavaNotesRepository extends CrudRepository<JavaNotes, Long> {
	/**
	 * 创建一个笔记
	 */
	@Modifying
	@Transactional
	@Query(value = "insert into java_notes (title, content, imagekey, timestamp) values (?1, ?2, ?3, ?4)", nativeQuery = true)
	int insertNote(String title, String content, String imagekey, long timestamp);
}
