/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.haibao.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * haibaoEntity
 * @author mxc
 * @version 2017-08-11
 */
public class Haibao extends DataEntity<Haibao> {
	
	private static final long serialVersionUID = 1L;
	private String imgurl;		// 海报图片
	
	public Haibao() {
		super();
	}

	public Haibao(String id){
		super(id);
	}

	@ExcelField(title="海报图片", align=2, sort=1)
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
}