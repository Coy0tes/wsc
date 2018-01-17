/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goods.entity;

import com.jeeplus.modules.goodscategory.entity.GoodsCategory;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品Entity
 * @author mxc
 * @version 2017-05-11
 */
public class Goods extends DataEntity<Goods> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商品名称
	private GoodsCategory category;		// 商品分类
	private Integer kcsl;		// 库存数量
	private String ison;		// 是否上架
	private String sfkydjq;		//是否可用代金券
	private String sffhyj;		//是否返还佣金
	private String yunfei;		//运费设置
	private String xjrq;		// 下架日期
	private String sfyj;		// 是否永久上架
	private String mainpicurl;		// 商品主图(建议640*640)
	private String pictures;		//商品图片展示
	private String contents;		// 商品图片&描述
	private Double price;		//价格
	private Integer sort;		//排序
	private String isyue;		// 是否使用余额
	private Integer xgnum;		// 选购数量
	

	private List<GoodsGuige> goodsGuigeList = Lists.newArrayList();		// 子表列表
	
	public Goods() {
		super();
	}

	public Goods(String id){
		super(id);
	}

	@ExcelField(title="商品名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="商品分类不能为空")
	@ExcelField(title="商品分类", align=2, sort=2)
	public GoodsCategory getCategory() {
		return category;
	}

	public void setCategory(GoodsCategory category) {
		this.category = category;
	}
	
	@ExcelField(title="是否上架", dictType="yes_no", align=2, sort=3)
	public String getIson() {
		return ison;
	}

	public void setIson(String ison) {
		this.ison = ison;
	}

	@NotNull(message="是否返还佣金不能为空")
	@ExcelField(title="是否返还佣金", dictType="yes_no", align=2, sort=4)
	public String getSffhyj() {
		return sffhyj;
	}

	public void setSffhyj(String sffhyj) {
		this.sffhyj = sffhyj;
	}
	
	@NotNull(message="是否可用代金券不能为空")
	@ExcelField(title="是否可用代金券", dictType="yes_no", align=2, sort=5)
	public String getSfkydjq() {
		return sfkydjq;
	}

	public void setSfkydjq(String sfkydjq) {
		this.sfkydjq = sfkydjq;
	}

	@NotNull(message="运费选项不能为空")
	@ExcelField(title="是否使用运费", dictType="yes_no", align=2, sort=6)
	public String getYunfei() {
		return yunfei;
	}

	public void setYunfei(String yunfei) {
		this.yunfei = yunfei;
	}
	
	@NotNull(message="库存数量不能为空")
	@ExcelField(title="库存数量", align=2, sort=7)
	public Integer getKcsl() {
		return kcsl;
	}

	public void setKcsl(Integer kcsl) {
		this.kcsl = kcsl;
	}
	
	@ExcelField(title="下架日期", align=2, sort=8)
	public String getXjrq() {
		return xjrq;
	}

	public void setXjrq(String xjrq) {
		this.xjrq = xjrq;
	}
	
	@ExcelField(title="是否永久上架", dictType="yes_no", align=2, sort=9)
	public String getSfyj() {
		return sfyj;
	}

	public void setSfyj(String sfyj) {
		this.sfyj = sfyj;
	}
	
	public String getMainpicurl() {
		return mainpicurl;
	}

	public void setMainpicurl(String mainpicurl) {
		this.mainpicurl = mainpicurl;
	}
	
	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
	@ExcelField(title="价格", align=2, sort=10)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public List<GoodsGuige> getGoodsGuigeList() {
		return goodsGuigeList;
	}

	@ExcelField(title="排序", align=2, sort=11)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getIsyue() {
		return isyue;
	}

	public void setIsyue(String isyue) {
		this.isyue = isyue;
	}

	public Integer getXgnum() {
		return xgnum;
	}

	public void setXgnum(Integer xgnum) {
		this.xgnum = xgnum;
	}

	public void setGoodsGuigeList(List<GoodsGuige> goodsGuigeList) {
		this.goodsGuigeList = goodsGuigeList;
	}
}