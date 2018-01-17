/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goodsorder.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.goodsorder.entity.GoodsOrder;

/**
 * 订单管理DAO接口
 * @author mxc
 * @version 2017-05-26
 */
@MyBatisDao
public interface GoodsOrderDao extends CrudDao<GoodsOrder> {

	
}