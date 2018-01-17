/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.news.entity;

import com.jeeplus.modules.dmnewscategory.entity.DmNewsCategory;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 新闻管理Entity
 * @author mxc
 * @version 2017-05-10
 */
public class DmNews extends DataEntity<DmNews> {
	
	private static final long serialVersionUID = 1L;
	private DmNewsCategory category;		// 所属分类
	private String title;		// 新闻标题
	private String imgurl;		// 新闻主图
	private String contents;		// 新闻内容
	private Integer sort;		// 排序
	
	public DmNews() {
		super();
	}

	public DmNews(String id){
		super(id);
	}

	@NotNull(message="所属分类不能为空")
	@ExcelField(title="所属分类", align=2, sort=1)
	public DmNewsCategory getCategory() {
		return category;
	}

	public void setCategory(DmNewsCategory category) {
		this.category = category;
	}
	
	@ExcelField(title="新闻标题", align=2, sort=2)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="新闻主图", align=2, sort=3)
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	@ExcelField(title="新闻内容", align=2, sort=4)
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	@NotNull(message="排序不能为空")
	@ExcelField(title="排序", align=2, sort=5)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}