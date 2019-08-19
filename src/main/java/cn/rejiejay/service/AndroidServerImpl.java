package cn.rejiejay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rejiejay.dataaccessobject.AndroidRecordEventRepository;

/**
 *安卓端支持（部分支持web端  实现类
 * 
 * @author _rejeijay
 * @Date 2019-7-16 16:11:38
 */
@Service
public class AndroidServerImpl implements AndroidServer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AndroidRecordEventRepository androidRecordEventRepository;
	
	/**
	 * 统计 所有 list 数据
	 */
	public long listRecordEventCount() {
		return androidRecordEventRepository.count();
	}
}
