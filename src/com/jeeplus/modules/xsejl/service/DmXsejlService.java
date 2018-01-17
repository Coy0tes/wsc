/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.xsejl.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.xsejl.entity.DmXsejl;
import com.jeeplus.modules.xsejl.dao.DmXsejlDao;

/**
 * 销售额奖励Service
 * @author zhaoliangdong
 * @version 2017-06-06
 */
@Service
@Transactional(readOnly = true)
public class DmXsejlService extends CrudService<DmXsejlDao, DmXsejl> {

	public DmXsejl get(String id) {
		return super.get(id);
	}
	
	public List<DmXsejl> findList(DmXsejl dmXsejl) {
		return super.findList(dmXsejl);
	}
	
	public Page<DmXsejl> findPage(Page<DmXsejl> page, DmXsejl dmXsejl) {
		return super.findPage(page, dmXsejl);
	}
	
	@Transactional(readOnly = false)
	public void save(DmXsejl dmXsejl) {
		super.save(dmXsejl);
	}
	
	@Transactional(readOnly = false)
	public void delete(DmXsejl dmXsejl) {
		super.delete(dmXsejl);
	}
	
	
	
	
}