/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.memberchongzhi.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 充值模块Entity
 * @author mxc
 * @version 2017-05-15
 */
public class MemberChongzhi extends DataEntity<MemberChongzhi> {
	
	private static final long serialVersionUID = 1L;
	private String wxopenid;		// 充值人微信Openid
	private String headimgurl;
	private String nickname;
	private String name;		// 姓名
	private String mobile;		// 手机号
	private Double jine;		// 充值金额
	private Double zsjine;		// 赠送金额
	
	public MemberChongzhi() {
		super();
	}

	public MemberChongzhi(String id){
		super(id);
	}

	@ExcelField(title="充值人微信Openid", align=2, sort=1)
	public String getWxopenid() {
		return wxopenid;
	}

	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}
	
	@ExcelField(title="姓名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="手机号", align=2, sort=3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="充值金额", align=2, sort=4)
	public Double getJine() {
		return jine;
	}

	public void setJine(Double jine) {
		this.jine = jine;
	}
	
	@ExcelField(title="赠送金额", align=2, sort=5)
	public Double getZsjine() {
		return zsjine;
	}

	public void setZsjine(Double zsjine) {
		this.zsjine = zsjine;
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