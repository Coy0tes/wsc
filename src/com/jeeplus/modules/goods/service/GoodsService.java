/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.goods.entity.Goods;
import com.jeeplus.modules.goods.dao.GoodsDao;
import com.jeeplus.modules.goods.entity.GoodsGuige;
import com.jeeplus.modules.goods.dao.GoodsGuigeDao;
import com.jeeplus.modules.goodscategory.entity.GoodsCategory;

/**
 * 商品Service
 * @author mxc
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class GoodsService extends CrudService<GoodsDao, Goods> {

	@Autowired
	private GoodsGuigeDao goodsGuigeDao;
	
	public Goods get(String id) {
		Goods goods = super.get(id);
		goods.setGoodsGuigeList(goodsGuigeDao.findList(new GoodsGuige(goods)));
		return goods;
	}
	
	public List<Goods> findList(Goods goods) {
		return super.findList(goods);
	}
	
	public Page<Goods> findPage(Page<Goods> page, Goods goods) {
		return super.findPage(page, goods);
	}
	
	@Transactional(readOnly = false)
	public void save(Goods goods) {
		super.save(goods);
		for (GoodsGuige goodsGuige : goods.getGoodsGuigeList()){
			if (goodsGuige.getId() == null){
				continue;
			}
			if (GoodsGuige.DEL_FLAG_NORMAL.equals(goodsGuige.getDelFlag())){
				if (StringUtils.isBlank(goodsGuige.getId())){
					goodsGuige.setMain(goods);
					goodsGuige.preInsert();
					goodsGuigeDao.insert(goodsGuige);
				}else{
					goodsGuige.preUpdate();
					goodsGuigeDao.update(goodsGuige);
				}
			}else{
				goodsGuigeDao.delete(goodsGuige);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Goods goods) {
		super.delete(goods);
		goodsGuigeDao.delete(new GoodsGuige(goods));
	}

	public Page<GoodsCategory> findPageBycategory(Page<GoodsCategory> page,	GoodsCategory category) {
		category.setPage(page);
		page.setList(dao.findListBycategory(category));
		return page;
	}
	
}