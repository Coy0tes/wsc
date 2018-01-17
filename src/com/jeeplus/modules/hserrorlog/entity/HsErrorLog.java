/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hserrorlog.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 系统日志Entity
 * @author mxc
 * @version 2017-05-28
 */
public class HsErrorLog extends DataEntity<HsErrorLog> {
	
	private static final long serialVersionUID = 1L;
	private String openid;		// openid
	private String contents;		// 日志详情
	
	public HsErrorLog() {
		super();
	}

	public HsErrorLog(String id){
		super(id);
	}

	@ExcelField(title="openid", align=2, sort=1)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@ExcelField(title="日志详情", align=2, sort=2)
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
}