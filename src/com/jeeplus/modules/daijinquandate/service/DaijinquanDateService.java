/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daijinquandate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.daijinquandate.entity.DaijinquanDate;
import com.jeeplus.modules.daijinquandate.dao.DaijinquanDateDao;

/**
 * 代金券活动期限设置Service
 * @author mxc
 * @version 2017-05-15
 */
@Service
@Transactional(readOnly = true)
public class DaijinquanDateService extends CrudService<DaijinquanDateDao, DaijinquanDate> {

	public DaijinquanDate get(String id) {
		return super.get(id);
	}
	
	public List<DaijinquanDate> findList(DaijinquanDate daijinquanDate) {
		return super.findList(daijinquanDate);
	}
	
	public Page<DaijinquanDate> findPage(Page<DaijinquanDate> page, DaijinquanDate daijinquanDate) {
		return super.findPage(page, daijinquanDate);
	}
	
	@Transactional(readOnly = false)
	public void save(DaijinquanDate daijinquanDate) {
		super.save(daijinquanDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(DaijinquanDate daijinquanDate) {
		super.delete(daijinquanDate);
	}
	
	
	
	
}