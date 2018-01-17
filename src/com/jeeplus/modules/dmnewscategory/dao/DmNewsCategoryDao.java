/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmnewscategory.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.dmnewscategory.entity.DmNewsCategory;

/**
 * 新闻分类DAO接口
 * @author admin
 * @version 2017-05-10
 */
@MyBatisDao
public interface DmNewsCategoryDao extends CrudDao<DmNewsCategory> {

	
}