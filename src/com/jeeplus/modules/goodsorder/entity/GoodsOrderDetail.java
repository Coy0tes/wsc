/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goodsorder.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订单管理Entity
 * @author mxc
 * @version 2017-05-26
 */
public class GoodsOrderDetail extends DataEntity<GoodsOrderDetail> {
	
	private static final long serialVersionUID = 1L;
	private GoodsOrder orderid;		// 对应总表主键ID 父类
	private String goodsid;		// 商品ID
	private String goodsname;		// 商品名称
	private String goodsguigeid;		// 商品规格ID
	private String goodsguigename;		// 商品规格
	private Integer goodscount;		// 购买数量
	private Double goodsprice;		// 商品单价
	
	public GoodsOrderDetail() {
		super();
	}

	public GoodsOrderDetail(String id){
		super(id);
	}

	public GoodsOrderDetail(GoodsOrder orderid){
		this.orderid = orderid;
	}

	public GoodsOrder getOrderid() {
		return orderid;
	}

	public void setOrderid(GoodsOrder orderid) {
		this.orderid = orderid;
	}
	
	@ExcelField(title="商品ID", align=2, sort=2)
	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	
	@ExcelField(title="商品名称", align=2, sort=3)
	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	
	@ExcelField(title="商品规格ID", align=2, sort=4)
	public String getGoodsguigeid() {
		return goodsguigeid;
	}

	public void setGoodsguigeid(String goodsguigeid) {
		this.goodsguigeid = goodsguigeid;
	}
	
	@ExcelField(title="商品规格", align=2, sort=5)
	public String getGoodsguigename() {
		return goodsguigename;
	}

	public void setGoodsguigename(String goodsguigename) {
		this.goodsguigename = goodsguigename;
	}
	
	@ExcelField(title="购买数量", align=2, sort=6)
	public Integer getGoodscount() {
		return goodscount;
	}

	public void setGoodscount(Integer goodscount) {
		this.goodscount = goodscount;
	}
	
	@ExcelField(title="商品单价", align=2, sort=7)
	public Double getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(Double goodsprice) {
		this.goodsprice = goodsprice;
	}
	
}