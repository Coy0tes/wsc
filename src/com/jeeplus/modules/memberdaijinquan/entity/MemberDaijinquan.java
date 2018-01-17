/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.memberdaijinquan.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 代金券使用Entity
 * @author mxc
 * @version 2017-05-15
 */
public class MemberDaijinquan extends DataEntity<MemberDaijinquan> {
	
	private static final long serialVersionUID = 1L;
	private String wxopenid;		// 微信Openid
	private String name;		// 姓名
	private String mobile;		// 手机号
	private String laiyuan;		// 代金券来源(消费赠送/商场发放)
	private String orderid;		// 来源订单
	private Double jine;		// 代金券金额
	private String syzt;		// 使用状态
	
	public MemberDaijinquan() {
		super();
	}

	public MemberDaijinquan(String id){
		super(id);
	}

	@ExcelField(title="微信Openid", align=2, sort=1)
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
	
	@ExcelField(title="代金券来源(消费赠送/商场发放)", dictType="laiyuan", align=2, sort=4)
	public String getLaiyuan() {
		return laiyuan;
	}

	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}
	
	@ExcelField(title="来源订单", align=2, sort=5)
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	@ExcelField(title="代金券金额", align=2, sort=6)
	public Double getJine() {
		return jine;
	}

	public void setJine(Double jine) {
		this.jine = jine;
	}
	
	@ExcelField(title="使用状态", dictType="djqsyzt", align=2, sort=7)
	public String getSyzt() {
		return syzt;
	}

	public void setSyzt(String syzt) {
		this.syzt = syzt;
	}
	
}