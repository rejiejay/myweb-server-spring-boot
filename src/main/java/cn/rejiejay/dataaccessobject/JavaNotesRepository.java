package cn.rejiejay.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.rejiejay.dataaccessobject.JavaNotes;

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

	/**
	 * 时间排序 查询第N页 每页10条
	 */
	@Query(value = "select * from java_notes order by timestamp desc limit ?1, 10", nativeQuery = true)
	List<JavaNotes> findJavaNotesByPageNo(int pageNo);

	/**
	 * 随机查询 N 条记录
	 * 
	 * @param total 查询多少条数据库的数据
	 */
	@Query(value = "select * from java_notes order by rand() limit ?1", nativeQuery = true)
	List<JavaNotes> findJavaNotesByRandom(int total);
}
