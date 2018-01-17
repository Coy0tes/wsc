/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.xsejl.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 销售额奖励Entity
 * @author zhaoliangdong
 * @version 2017-06-06
 */
public class DmXsejl extends DataEntity<DmXsejl> {
	
	private static final long serialVersionUID = 1L;
	private Double jmtced;		// 加盟提成额度(元)
	private Double jmtcbl;		// 加盟提成比例(%)
	
	public DmXsejl() {
		super();
	}

	public DmXsejl(String id){
		super(id);
	}

	@NotNull(message="加盟提成额度(元)不能为空")
	@ExcelField(title="加盟提成额度(元)", align=2, sort=1)
	public Double getJmtced() {
		return jmtced;
	}

	public void setJmtced(Double jmtced) {
		this.jmtced = jmtced;
	}
	
	@NotNull(message="奖励金额(元)不能为空")
	@ExcelField(title="奖励金额(元)", align=2, sort=2)
	public Double getJmtcbl() {
		return jmtcbl;
	}

	public void setJmtcbl(Double jmtcbl) {
		this.jmtcbl = jmtcbl;
	}
	
}