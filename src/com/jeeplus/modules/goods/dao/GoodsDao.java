/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goods.dao;

import com.jeeplus.modules.goodscategory.entity.GoodsCategory;
import java.util.List;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.goods.entity.Goods;

/**
 * 商品DAO接口
 * @author mxc
 * @version 2017-05-11
 */
@MyBatisDao
public interface GoodsDao extends CrudDao<Goods> {

	public List<GoodsCategory> findListBycategory(GoodsCategory category);
	
}