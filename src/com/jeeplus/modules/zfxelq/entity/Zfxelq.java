/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.zfxelq.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 总分销额领取Entity
 * @author zhaoliangdong
 * @version 2017-06-10
 */
public class Zfxelq extends DataEntity<Zfxelq> {
	
	private static final long serialVersionUID = 1L;
	private String wxopenid;		// 微信Openid
	private Double ljjine;		// 累计金额
	private Double bili;		// 奖励比例
	private Double jine;		// 实际奖励金额
	private Date sdate;		// 领取时间
	private String headimgurl;
	private String nickname;
	
	public Zfxelq() {
		super();
	}

	public Zfxelq(String id){
		super(id);
	}

	@ExcelField(title="微信Openid", align=2, sort=1)
	public String getWxopenid() {
		return wxopenid;
	}

	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}
	
	@ExcelField(title="累计金额", align=2, sort=2)
	public Double getLjjine() {
		return ljjine;
	}

	public void setLjjine(Double ljjine) {
		this.ljjine = ljjine;
	}
	
	@ExcelField(title="奖励比例", align=2, sort=3)
	public Double getBili() {
		return bili;
	}

	public void setBili(Double bili) {
		this.bili = bili;
	}
	
	@ExcelField(title="实际奖励金额", align=2, sort=4)
	public Double getJine() {
		return jine;
	}

	public void setJine(Double jine) {
		this.jine = jine;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="领取时间", align=2, sort=5)
	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}