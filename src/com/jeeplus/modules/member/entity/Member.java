/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.member.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会员Entity
 * @author mxc
 * @version 2017-05-15
 */
public class Member extends DataEntity<Member> {
	
	private static final long serialVersionUID = 1L;
	private String wxopenid;		// 微信Openid
	private String name;		// 姓名
	private String mobile;		// 手机号
	private String shrdh;		//收货人电话
	private String address;		// 收货地址
	private String tjrwxopenid;		// 推荐人微信Openid
	private Double yue;		// 余额
	private Double yongjin;		// 佣金
	private Double chongzhijine;	//充值金额
	private Double zsjine;		//赠送金额
	private String province;
	private String city;
	private String county;
	private String haibaourl;	//海报url
	private String headimgurl;
	private String nickname;	
	private Double yongjinytx;  //已提现佣金
	private Double yongjinls;   //历史佣金
	public Member() {
		super();
	}

	public Member(String id){
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
	
	@ExcelField(title="收货人电话", align=2, sort=4)
	public String getShrdh() {
		return shrdh;
	}

	public void setShrdh(String shrdh) {
		this.shrdh = shrdh;
	}
	
	@ExcelField(title="收货地址", align=2, sort=5)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="推荐人微信Openid", align=2, sort=6)
	public String getTjrwxopenid() {
		return tjrwxopenid;
	}

	public void setTjrwxopenid(String tjrwxopenid) {
		this.tjrwxopenid = tjrwxopenid;
	}
	
	@ExcelField(title="余额", align=2, sort=7)
	public Double getYue() {
		return yue;
	}

	public void setYue(Double yue) {
		this.yue = yue;
	}
	
	@ExcelField(title="佣金", align=2, sort=8)
	public Double getYongjin() {
		return yongjin;
	}

	public void setYongjin(Double yongjin) {
		this.yongjin = yongjin;
	}

	@ExcelField(title="省", align=2, sort=9)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@ExcelField(title="市", align=2, sort=10)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@ExcelField(title="区", align=2, sort=11)
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	//充值余额  get/set
	public Double getChongzhijine() {
		return chongzhijine;
	}
	
	public void setChongzhijine(Double chongzhijine) {
		this.chongzhijine = chongzhijine;
	}

	public Double getZsjine() {
		return zsjine;
	}

	public void setZsjine(Double zsjine) {
		this.zsjine = zsjine;
	}

	@ExcelField(title="海报url", align=2, sort=12)
	public String getHaibaourl() {
		return haibaourl;
	}
	
	public void setHaibaourl(String haibaourl) {
		this.haibaourl = haibaourl;
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

	public Double getYongjinytx() {
		return yongjinytx;
	}

	public void setYongjinytx(Double yongjinytx) {
		this.yongjinytx = yongjinytx;
	}

	public Double getYongjinls() {
		return yongjinls;
	}

	public void setYongjinls(Double yongjinls) {
		this.yongjinls = yongjinls;
	}
}