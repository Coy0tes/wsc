/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmfenyongbili.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.dmfenyongbili.entity.DmFenyongbili;
import com.jeeplus.modules.dmfenyongbili.dao.DmFenyongbiliDao;

/**
 * 分佣比例Service
 * @author admin
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class DmFenyongbiliService extends CrudService<DmFenyongbiliDao, DmFenyongbili> {

	public DmFenyongbili get(String id) {
		return super.get(id);
	}
	
	public List<DmFenyongbili> findList(DmFenyongbili dmFenyongbili) {
		return super.findList(dmFenyongbili);
	}
	
	public Page<DmFenyongbili> findPage(Page<DmFenyongbili> page, DmFenyongbili dmFenyongbili) {
		return super.findPage(page, dmFenyongbili);
	}
	
	@Transactional(readOnly = false)
	public void save(DmFenyongbili dmFenyongbili) {
		super.save(dmFenyongbili);
	}
	
	@Transactional(readOnly = false)
	public void delete(DmFenyongbili dmFenyongbili) {
		super.delete(dmFenyongbili);
	}
	
	
	
	
}