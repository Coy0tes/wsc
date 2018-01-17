/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmchongzhidangci.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 充值金额档次： String id  double jine Entity
 * @author mxc
 * @version 2017-05-09
 */
public class DmChongzhidangci extends DataEntity<DmChongzhidangci> {
	
	private static final long serialVersionUID = 1L;
	private Double jine;		// 金额
	
	public DmChongzhidangci() {
		super();
	}

	public DmChongzhidangci(String id){
		super(id);
	}

	@ExcelField(title="金额", align=2, sort=1)
	public Double getJine() {
		return jine;
	}

	public void setJine(Double jine) {
		this.jine = jine;
	}
	
}