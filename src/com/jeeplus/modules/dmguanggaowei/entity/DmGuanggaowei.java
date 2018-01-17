/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmguanggaowei.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 广告位管理Entity
 * @author mxc
 * @version 2017-05-23
 */
public class DmGuanggaowei extends DataEntity<DmGuanggaowei> {
	
	private static final long serialVersionUID = 1L;
	private String picture;		// 广告位图片
	private String imgurl;		// 图片链接
	private Integer sort;		// 排序
	
	public DmGuanggaowei() {
		super();
	}

	public DmGuanggaowei(String id){
		super(id);
	}

	@ExcelField(title="广告位图片", align=2, sort=1)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@ExcelField(title="图片链接", align=2, sort=2)
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	@ExcelField(title="排序", align=2, sort=3)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}