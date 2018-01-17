/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goodsyunfeishezhi.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 运费管理Entity
 * @author mxc
 * @version 2017-05-28
 */
public class GoodsYunfeishezhi extends DataEntity<GoodsYunfeishezhi> {
	
	private static final long serialVersionUID = 1L;
	private String yunfeishezhi;		// 运费设置
	
	public GoodsYunfeishezhi() {
		super();
	}

	public GoodsYunfeishezhi(String id){
		super(id);
	}

	@ExcelField(title="运费设置", align=2, sort=1)
	public String getYunfeishezhi() {
		return yunfeishezhi;
	}

	public void setYunfeishezhi(String yunfeishezhi) {
		this.yunfeishezhi = yunfeishezhi;
	}
	
}