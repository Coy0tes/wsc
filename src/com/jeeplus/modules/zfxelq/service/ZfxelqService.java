/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.zfxelq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.zfxelq.entity.Zfxelq;
import com.jeeplus.modules.zfxelq.dao.ZfxelqDao;

/**
 * 总分销额领取Service
 * @author zhaoliangdong
 * @version 2017-06-10
 */
@Service
@Transactional(readOnly = true)
public class ZfxelqService extends CrudService<ZfxelqDao, Zfxelq> {

	public Zfxelq get(String id) {
		return super.get(id);
	}
	
	public List<Zfxelq> findList(Zfxelq zfxelq) {
		return super.findList(zfxelq);
	}
	
	public Page<Zfxelq> findPage(Page<Zfxelq> page, Zfxelq zfxelq) {
		return super.findPage(page, zfxelq);
	}
	
	@Transactional(readOnly = false)
	public void save(Zfxelq zfxelq) {
		super.save(zfxelq);
	}
	
	@Transactional(readOnly = false)
	public void delete(Zfxelq zfxelq) {
		super.delete(zfxelq);
	}
	
	
	
	
}