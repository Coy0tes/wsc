/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goodsyunfeishezhi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.goodsyunfeishezhi.entity.GoodsYunfeishezhi;
import com.jeeplus.modules.goodsyunfeishezhi.dao.GoodsYunfeishezhiDao;

/**
 * 运费管理Service
 * @author mxc
 * @version 2017-05-28
 */
@Service
@Transactional(readOnly = true)
public class GoodsYunfeishezhiService extends CrudService<GoodsYunfeishezhiDao, GoodsYunfeishezhi> {

	public GoodsYunfeishezhi get(String id) {
		return super.get(id);
	}
	
	public List<GoodsYunfeishezhi> findList(GoodsYunfeishezhi goodsYunfeishezhi) {
		return super.findList(goodsYunfeishezhi);
	}
	
	public Page<GoodsYunfeishezhi> findPage(Page<GoodsYunfeishezhi> page, GoodsYunfeishezhi goodsYunfeishezhi) {
		return super.findPage(page, goodsYunfeishezhi);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsYunfeishezhi goodsYunfeishezhi) {
		super.save(goodsYunfeishezhi);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsYunfeishezhi goodsYunfeishezhi) {
		super.delete(goodsYunfeishezhi);
	}
	
	
	
	
}