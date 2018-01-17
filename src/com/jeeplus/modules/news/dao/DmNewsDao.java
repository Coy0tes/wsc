/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.news.dao;

import com.jeeplus.modules.dmnewscategory.entity.DmNewsCategory;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.news.entity.DmNews;

/**
 * 新闻管理DAO接口
 * @author mxc
 * @version 2017-05-10
 */
@MyBatisDao
public interface DmNewsDao extends CrudDao<DmNews> {

	public List<DmNewsCategory> findListBycategory(DmNewsCategory category);
	
}