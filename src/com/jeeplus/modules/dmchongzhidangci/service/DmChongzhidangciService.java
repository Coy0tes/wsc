/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmchongzhidangci.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.dmchongzhidangci.entity.DmChongzhidangci;
import com.jeeplus.modules.dmchongzhidangci.dao.DmChongzhidangciDao;

/**
 * 充值金额档次： String id  double jine Service
 * @author mxc
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class DmChongzhidangciService extends CrudService<DmChongzhidangciDao, DmChongzhidangci> {

	public DmChongzhidangci get(String id) {
		return super.get(id);
	}
	
	public List<DmChongzhidangci> findList(DmChongzhidangci dmChongzhidangci) {
		return super.findList(dmChongzhidangci);
	}
	
	public Page<DmChongzhidangci> findPage(Page<DmChongzhidangci> page, DmChongzhidangci dmChongzhidangci) {
		return super.findPage(page, dmChongzhidangci);
	}
	
	@Transactional(readOnly = false)
	public void save(DmChongzhidangci dmChongzhidangci) {
		super.save(dmChongzhidangci);
	}
	
	@Transactional(readOnly = false)
	public void delete(DmChongzhidangci dmChongzhidangci) {
		super.delete(dmChongzhidangci);
	}
	
	
	
	
}