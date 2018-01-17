/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.shangjia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.shangjia.entity.Shangjia;
import com.jeeplus.modules.shangjia.dao.ShangjiaDao;

/**
 * 商家管理Service
 * @author mxc
 * @version 2017-05-16
 */
@Service
@Transactional(readOnly = true)
public class ShangjiaService extends CrudService<ShangjiaDao, Shangjia> {

	public Shangjia get(String id) {
		return super.get(id);
	}
	
	public List<Shangjia> findList(Shangjia shangjia) {
		return super.findList(shangjia);
	}
	
	public Page<Shangjia> findPage(Page<Shangjia> page, Shangjia shangjia) {
		return super.findPage(page, shangjia);
	}
	
	@Transactional(readOnly = false)
	public void save(Shangjia shangjia) {
		super.save(shangjia);
	}
	
	@Transactional(readOnly = false)
	public void delete(Shangjia shangjia) {
		super.delete(shangjia);
	}
	
	
	
	
}