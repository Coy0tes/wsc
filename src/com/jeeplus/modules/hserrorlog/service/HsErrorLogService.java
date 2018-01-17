/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hserrorlog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.hserrorlog.entity.HsErrorLog;
import com.jeeplus.modules.hserrorlog.dao.HsErrorLogDao;

/**
 * 系统日志Service
 * @author mxc
 * @version 2017-05-28
 */
@Service
@Transactional(readOnly = true)
public class HsErrorLogService extends CrudService<HsErrorLogDao, HsErrorLog> {

	public HsErrorLog get(String id) {
		return super.get(id);
	}
	
	public List<HsErrorLog> findList(HsErrorLog hsErrorLog) {
		return super.findList(hsErrorLog);
	}
	
	public Page<HsErrorLog> findPage(Page<HsErrorLog> page, HsErrorLog hsErrorLog) {
		return super.findPage(page, hsErrorLog);
	}
	
	@Transactional(readOnly = false)
	public void save(HsErrorLog hsErrorLog) {
		super.save(hsErrorLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(HsErrorLog hsErrorLog) {
		super.delete(hsErrorLog);
	}
	
	
	
	
}