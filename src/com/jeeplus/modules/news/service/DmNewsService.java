/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.news.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.dmnewscategory.entity.DmNewsCategory;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.news.entity.DmNews;
import com.jeeplus.modules.news.dao.DmNewsDao;

/**
 * 新闻管理Service
 * @author mxc
 * @version 2017-05-10
 */
@Service
@Transactional(readOnly = true)
public class DmNewsService extends CrudService<DmNewsDao, DmNews> {

	public DmNews get(String id) {
		return super.get(id);
	}
	
	public List<DmNews> findList(DmNews dmNews) {
		return super.findList(dmNews);
	}
	
	public Page<DmNews> findPage(Page<DmNews> page, DmNews dmNews) {
		return super.findPage(page, dmNews);
	}
	
	@Transactional(readOnly = false)
	public void save(DmNews dmNews) {
		super.save(dmNews);
	}
	
	@Transactional(readOnly = false)
	public void delete(DmNews dmNews) {
		super.delete(dmNews);
	}
	
	public Page<DmNewsCategory> findPageBycategory(Page<DmNewsCategory> page, DmNewsCategory category) {
		category.setPage(page);
		page.setList(dao.findListBycategory(category));
		return page;
	}
	
	
	
}