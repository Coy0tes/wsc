/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.haibao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.haibao.entity.Haibao;
import com.jeeplus.modules.haibao.dao.HaibaoDao;

/**
 * haibaoService
 * @author mxc
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class HaibaoService extends CrudService<HaibaoDao, Haibao> {

	public Haibao get(String id) {
		return super.get(id);
	}
	
	public List<Haibao> findList(Haibao haibao) {
		return super.findList(haibao);
	}
	
	public Page<Haibao> findPage(Page<Haibao> page, Haibao haibao) {
		return super.findPage(page, haibao);
	}
	
	@Transactional(readOnly = false)
	public void save(Haibao haibao) {
		super.save(haibao);
	}
	
	@Transactional(readOnly = false)
	public void delete(Haibao haibao) {
		super.delete(haibao);
	}
	
	
	
	
}