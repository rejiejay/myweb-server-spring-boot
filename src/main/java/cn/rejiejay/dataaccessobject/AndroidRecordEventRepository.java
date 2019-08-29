package cn.rejiejay.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.rejiejay.dataaccessobject.AndroidRecordEvents;

/**
 * 安卓端支持（部分支持web端 实体类 的 ORM 接口方法
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
	/**
	 * 根据 数据 类型（时间排序 查询第N页 每页10条
	 */
	@Query(value = "select * from android_record_events where type=?1 order by timestamp desc limit ?2, 10", nativeQuery = true)
	List<AndroidRecordEvents> findTypeRecordEventByPageNo(String dataType, int pageNo);
	/**
	 * 根据 标签 类型（时间排序 查询第N页 每页10条
	 */
	@Query(value = "select * from android_record_events where tag=?1 order by timestamp desc limit ?2, 10", nativeQuery = true)
	List<AndroidRecordEvents> findTagRecordEventByPageNo(String dataTag, int pageNo);
	/**
	 * 根据 数据标签 类型（时间排序 查询第N页 每页10条
	 */
	@Query(value = "select * from android_record_events where type=?1 and tag=?2 order by timestamp desc limit ?3, 10", nativeQuery = true)
	List<AndroidRecordEvents> findTypeTagRecordEventByPageNo(String dataType, String dataTag, int pageNo);

	/**
	 * 随机查询 N 条记录
	 */
	@Query(value = "select * from android_record_events order by rand() limit ?1", nativeQuery = true)
	List<AndroidRecordEvents> findRecordEventByRandom(int total);
	/**
	 * 根据 数据 类型（随机查询 N 条记录
	 */
	@Query(value = "select * from android_record_events where type=?1 order by rand() limit ?2", nativeQuery = true)
	List<AndroidRecordEvents> findTypeRecordEventByRandom(String dataType, int total);
	/**
	 * 根据 标签 类型（随机查询 N 条记录
	 */
	@Query(value = "select * from android_record_events where tag=?1 order by rand() limit ?2", nativeQuery = true)
	List<AndroidRecordEvents> findTagRecordEventByRandom(String dataTag, int total);
	/**
	 * 根据 数据和标签 类型（随机查询 N 条记录
	 */
	@Query(value = "select * from android_record_events where type=?1 and tag=?2 order by rand() limit ?3", nativeQuery = true)
	List<AndroidRecordEvents> findTypeTagRecordEventByRandom(String dataType, String dataTag, int total);
	
	/**
	 * 根据 时间范围（查询第N页 每页10条
	 */
	@Query(value = "select * from android_record_events where timestamp>=?1 and timestamp<=?2 order by timestamp desc limit ?3, 10", nativeQuery = true)
	List<AndroidRecordEvents> listRecordEventByTime(long minTimestamp, long maxTimestamp, int pageNo);
	
	/**
	 * 新增记录
	 */
	@Modifying
	@Transactional
	@Query(value = "insert into android_record_events (type, recordtitle, recordmaterial, recordcontent, imageidentity, tag, timestamp, fullyear, month, week) values (\"record\", ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9)", nativeQuery = true)
	int insertRecord(String recordtitle, String recordmaterial, String recordcontent, String imageidentity, String tag,
			long timestamp, int fullyear, int month, int week);

	/**
	 * 编辑记录
	 */
	@Modifying
	@Transactional
	@Query(value = "update android_record_events set recordtitle=?2,recordmaterial=?3,recordcontent=?4,imageidentity=?5,tag=?6 where android_id=?1", nativeQuery = true)
	int updateRecord(int id, String recordtitle, String recordmaterial, String recordcontent, String imageidentity, String tag);

	/**
	 * 新增事件
	 */
	@Modifying
	@Transactional
	@Query(value = "insert into android_record_events (type, eventcause, eventprocess, eventresult, eventconclusion, imageidentity, timestamp, fullyear, month, week) values (\"event\", ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9)", nativeQuery = true)
	int insertEvent(String eventcause, String eventprocess, String eventresult, String eventconclusion, String imageidentity,
			long timestamp, int fullyear, int month, int week);

	/**
	 * 编辑事件
	 */
	@Modifying
	@Transactional
	@Query(value = "update android_record_events set eventcause=?2,eventprocess=?3,eventresult=?4,eventconclusion=?5,imageidentity=?6,timestamp=?7,fullyear=?8,month=?9,week=?10 where android_id=?1", nativeQuery = true)
	int updateEvent(int androidid, String eventcause, String eventprocess, String eventresult, String eventconclusion, String imageidentity, long timestamp, int fullyear,int month, int week);

	/**
	 * 统计 记录标签
	 */
	@Query(value = "select distinct tag from android_record_events;", nativeQuery = true)
	List<String> statisticRecordEventTag();
	
	/**
	 * 统计 多少种 年份标签
	 */
	@Query(value = "select distinct fullyear from android_record_events;", nativeQuery = true)
	List<Integer> statisticRecordEventFullyear();

	/**
	 * 统计 多少种 年份标签
	 */
	@Query(value = "select distinct fullyear from android_record_events;", nativeQuery = true)
	List<Integer> countRecordEventFullyear();

	/**
	 * 统计 年份 有多少条
	 */
	@Query(value = "select count(*) from android_record_events where fullyear=?1", nativeQuery = true)
	int countRecordEventFullyear(Integer fullyear);

	/**
	 * 统计 时间段 有多少条数据
	 */
	@Query(value = "select count(*) from android_record_events where timestamp>=?1 and timestamp<=?2", nativeQuery = true)
	int countRecordEventTimestamp(long minTimestamp, long maxTimestamp);
}
