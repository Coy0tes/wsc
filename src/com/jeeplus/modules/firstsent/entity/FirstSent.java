/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.firstsent.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * firstsentEntity
 * @author mxc
 * @version 2017-08-24
 */
public class FirstSent extends DataEntity<FirstSent> {
	
	private static final long serialVersionUID = 1L;
	private Double jine;		// 赠送金额
	private String begintime;		// 开始时间
	private String endtime;		// 结束时间
	
	public FirstSent() {
		super();
	}

	public FirstSent(String id){
		super(id);
	}

	@ExcelField(title="赠送金额", align=2, sort=1)
	public Double getJine() {
		return jine;
	}

	public void setJine(Double jine) {
		this.jine = jine;
	}
	
	@ExcelField(title="开始时间", align=2, sort=2)
	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	
	@ExcelField(title="结束时间", align=2, sort=3)
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}