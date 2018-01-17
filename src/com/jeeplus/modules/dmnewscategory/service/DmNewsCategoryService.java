/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmnewscategory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.dmnewscategory.entity.DmNewsCategory;
import com.jeeplus.modules.dmnewscategory.dao.DmNewsCategoryDao;

/**
 * 新闻分类Service
 * @author admin
 * @version 2017-05-10
 */
@Service
@Transactional(readOnly = true)
public class DmNewsCategoryService extends CrudService<DmNewsCategoryDao, DmNewsCategory> {

	public DmNewsCategory get(String id) {
		return super.get(id);
	}
	
	public List<DmNewsCategory> findList(DmNewsCategory dmNewsCategory) {
		return super.findList(dmNewsCategory);
	}
	
	public Page<DmNewsCategory> findPage(Page<DmNewsCategory> page, DmNewsCategory dmNewsCategory) {
		return super.findPage(page, dmNewsCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(DmNewsCategory dmNewsCategory) {
		super.save(dmNewsCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(DmNewsCategory dmNewsCategory) {
		super.delete(dmNewsCategory);
	}
	
	
	
	
}