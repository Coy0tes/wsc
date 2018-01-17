/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.firstsent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.firstsent.entity.FirstSent;
import com.jeeplus.modules.firstsent.dao.FirstSentDao;

/**
 * firstsentService
 * @author mxc
 * @version 2017-08-24
 */
@Service
@Transactional(readOnly = true)
public class FirstSentService extends CrudService<FirstSentDao, FirstSent> {

	public FirstSent get(String id) {
		return super.get(id);
	}
	
	public List<FirstSent> findList(FirstSent firstSent) {
		return super.findList(firstSent);
	}
	
	public Page<FirstSent> findPage(Page<FirstSent> page, FirstSent firstSent) {
		return super.findPage(page, firstSent);
	}
	
	@Transactional(readOnly = false)
	public void save(FirstSent firstSent) {
		super.save(firstSent);
	}
	
	@Transactional(readOnly = false)
	public void delete(FirstSent firstSent) {
		super.delete(firstSent);
	}
	
	
	
	
}