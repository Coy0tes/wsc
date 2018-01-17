/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daijinquandate.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 代金券活动期限设置Entity
 * @author mxc
 * @version 2017-05-15
 */
public class DaijinquanDate extends DataEntity<DaijinquanDate> {
	
	private static final long serialVersionUID = 1L;
	private String begindate;		// 赠送活动开始时间
	private String enddate;		// 赠送活动结束时间
	
	public DaijinquanDate() {
		super();
	}

	public DaijinquanDate(String id){
		super(id);
	}

	@ExcelField(title="赠送活动开始时间", align=2, sort=1)
	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	
	@ExcelField(title="赠送活动结束时间", align=2, sort=2)
	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
}