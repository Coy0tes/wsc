/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.shangjia.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商家管理Entity
 * @author mxc
 * @version 2017-05-16
 */
public class Shangjia extends DataEntity<Shangjia> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商家名称
	private String mobile;		// 联系电话
	private Double yue;		// 余额
	
	public Shangjia() {
		super();
	}

	public Shangjia(String id){
		super(id);
	}

	@ExcelField(title="商家名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="联系电话", align=2, sort=2)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="余额", align=2, sort=3)
	public Double getYue() {
		return yue;
	}

	public void setYue(Double yue) {
		this.yue = yue;
	}
	
}