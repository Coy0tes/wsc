/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goods.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品Entity
 * @author mxc
 * @version 2017-05-11
 */
public class GoodsGuige extends DataEntity<GoodsGuige> {
	
	private static final long serialVersionUID = 1L;
	private Goods main;		// 商品ID 父类
	private String name;		// 规格名
	private Integer kcsl;		// 库存
	private Double price;		// 价格
	private Double csprice;		// 促销价格
	private String imgurl;
	
	public GoodsGuige() {
		super();
	}

	public GoodsGuige(String id){
		super(id);
	}

	public GoodsGuige(Goods main){
		this.main = main;
	}

	public Goods getMain() {
		return main;
	}

	public void setMain(Goods main) {
		this.main = main;
	}
	
	@ExcelField(title="规格名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="库存不能为空")
	@ExcelField(title="库存", align=2, sort=3)
	public Integer getKcsl() {
		return kcsl;
	}

	public void setKcsl(Integer kcsl) {
		this.kcsl = kcsl;
	}
	
	@NotNull(message="价格不能为空")
	@ExcelField(title="价格", align=2, sort=4)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="促销价格", align=2, sort=5)
	public Double getCsprice() {
		return csprice;
	}

	public void setCsprice(Double csprice) {
		this.csprice = csprice;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
}