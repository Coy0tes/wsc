/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmnewscategory.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 新闻分类Entity
 * @author admin
 * @version 2017-05-10
 */
public class DmNewsCategory extends DataEntity<DmNewsCategory> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 分类名称
	private Integer sort;		// 排序
	
	public DmNewsCategory() {
		super();
	}

	public DmNewsCategory(String id){
		super(id);
	}

	@ExcelField(title="分类名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="排序不能为空")
	@ExcelField(title="排序", align=2, sort=2)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}