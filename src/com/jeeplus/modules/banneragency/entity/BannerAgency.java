/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.banneragency.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商城轮播图管理Entity
 * @author zhaoliangdong
 * @version 2017-05-25
 */
public class BannerAgency extends DataEntity<BannerAgency> {
	
	private static final long serialVersionUID = 1L;
	private String imgurl;		// 图片
	private Integer sort;		// 排序
	
	public BannerAgency() {
		super();
	}

	public BannerAgency(String id){
		super(id);
	}

	@ExcelField(title="图片", align=2, sort=8)
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	@ExcelField(title="排序", align=2, sort=9)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}