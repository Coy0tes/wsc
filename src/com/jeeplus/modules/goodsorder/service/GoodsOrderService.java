/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goodsorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.goodsorder.entity.GoodsOrder;
import com.jeeplus.modules.goodsorder.dao.GoodsOrderDao;
import com.jeeplus.modules.goodsorder.entity.GoodsOrderDetail;
import com.jeeplus.modules.goodsorder.dao.GoodsOrderDetailDao;

/**
 * 订单管理Service
 * @author mxc
 * @version 2017-05-26
 */
@Service
@Transactional(readOnly = true)
public class GoodsOrderService extends CrudService<GoodsOrderDao, GoodsOrder> {

	@Autowired
	private GoodsOrderDetailDao goodsOrderDetailDao;
	
	public GoodsOrder get(String id) {
		GoodsOrder goodsOrder = super.get(id);
		goodsOrder.setGoodsOrderDetailList(goodsOrderDetailDao.findList(new GoodsOrderDetail(goodsOrder)));
		return goodsOrder;
	}
	
	public List<GoodsOrder> findList(GoodsOrder goodsOrder) {
		return super.findList(goodsOrder);
	}
	
	public Page<GoodsOrder> findPage(Page<GoodsOrder> page, GoodsOrder goodsOrder) {
		return super.findPage(page, goodsOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsOrder goodsOrder) {
		super.save(goodsOrder);
		/*for (GoodsOrderDetail goodsOrderDetail : goodsOrder.getGoodsOrderDetailList()){
			if (goodsOrderDetail.getId() == null){
				continue;
			}
			if (GoodsOrderDetail.DEL_FLAG_NORMAL.equals(goodsOrderDetail.getDelFlag())){
				if (StringUtils.isBlank(goodsOrderDetail.getId())){
					goodsOrderDetail.setOrderid(goodsOrder);
					goodsOrderDetail.preInsert();
					goodsOrderDetailDao.insert(goodsOrderDetail);
				}else{
					goodsOrderDetail.preUpdate();
					goodsOrderDetailDao.update(goodsOrderDetail);
				}
			}else{
				goodsOrderDetailDao.delete(goodsOrderDetail);
			}
		}*/
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsOrder goodsOrder) {
		super.delete(goodsOrder);
		goodsOrderDetailDao.delete(new GoodsOrderDetail(goodsOrder));
	}
	
}