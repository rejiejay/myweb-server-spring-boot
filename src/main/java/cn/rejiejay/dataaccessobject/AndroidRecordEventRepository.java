package cn.rejiejay.dataaccessobject;

import org.springframework.data.repository.CrudRepository;

import cn.rejiejay.dataaccessobject.AndroidRecordEvents;

/**
 * 安卓端支持（部分支持web端 实体类  的 ORM 接口方法
 * 
 * @author _rejeijay
 * @Date 2019年7月16日16:28:05
 */
public interface AndroidRecordEventRepository extends CrudRepository<AndroidRecordEvents, Long> {
	
}
