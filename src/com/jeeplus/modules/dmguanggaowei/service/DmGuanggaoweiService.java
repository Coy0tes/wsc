/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmguanggaowei.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.dmguanggaowei.entity.DmGuanggaowei;
import com.jeeplus.modules.dmguanggaowei.dao.DmGuanggaoweiDao;

/**
 * 广告位管理Service
 * @author mxc
 * @version 2017-05-23
 */
@Service
@Transactional(readOnly = true)
public class DmGuanggaoweiService extends CrudService<DmGuanggaoweiDao, DmGuanggaowei> {

	public DmGuanggaowei get(String id) {
		return super.get(id);
	}
	
	public List<DmGuanggaowei> findList(DmGuanggaowei dmGuanggaowei) {
		return super.findList(dmGuanggaowei);
	}
	
	public Page<DmGuanggaowei> findPage(Page<DmGuanggaowei> page, DmGuanggaowei dmGuanggaowei) {
		return super.findPage(page, dmGuanggaowei);
	}
	
	@Transactional(readOnly = false)
	public void save(DmGuanggaowei dmGuanggaowei) {
		super.save(dmGuanggaowei);
	}
	
	@Transactional(readOnly = false)
	public void delete(DmGuanggaowei dmGuanggaowei) {
		super.delete(dmGuanggaowei);
	}
	
	
	
	
}