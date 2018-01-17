/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goodsorder.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订单管理Entity
 * @author mxc
 * @version 2017-05-26
 */
public class GoodsOrder extends DataEntity<GoodsOrder> {
	
	private static final long serialVersionUID = 1L;
	private String wxopenid;		// 微信Openid
	private String headimgurl;
	private String nickname;
	private String khname;		// 客户姓名
	private Double ddjg;		// 订单总价
	private String sfsydjq;		// 是否使用代金券
	private String djqid;		// 代金券ID
	private Double djqjine;		// 代金券金额
	private Double xfje;		// 实际支付金额
	private String lqfs;		// 领取方式
	private String lqyjsj;		// 领取/邮寄时间
	private String mobile;		// 联系方式
	private String address;		// 联系地址
	private String status;		// 订单状态
	private String wldh;		// 物流单号
	private Double yunfei;		//运费
	private String kdgs;		//快递公司
	

	private List<GoodsOrderDetail> goodsOrderDetailList = Lists.newArrayList();		// 子表列表
	
	public GoodsOrder() {
		super();
	}

	public GoodsOrder(String id){
		super(id);
	}

	@ExcelField(title="微信Openid", align=2, sort=1)
	public String getWxopenid() {
		return wxopenid;
	}

	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}
	
	@ExcelField(title="客户姓名", align=2, sort=2)
	public String getKhname() {
		return khname;
	}

	public void setKhname(String khname) {
		this.khname = khname;
	}
	
	@ExcelField(title="订单总价", align=2, sort=3)
	public Double getDdjg() {
		return ddjg;
	}

	public void setDdjg(Double ddjg) {
		this.ddjg = ddjg;
	}
	
	@ExcelField(title="是否使用代金券", align=2, sort=4)
	public String getSfsydjq() {
		return sfsydjq;
	}

	public void setSfsydjq(String sfsydjq) {
		this.sfsydjq = sfsydjq;
	}
	
	@ExcelField(title="代金券ID", align=2, sort=5)
	public String getDjqid() {
		return djqid;
	}

	public void setDjqid(String djqid) {
		this.djqid = djqid;
	}
	
	@ExcelField(title="代金券金额", align=2, sort=6)
	public Double getDjqjine() {
		return djqjine;
	}

	public void setDjqjine(Double djqjine) {
		this.djqjine = djqjine;
	}
	
	@ExcelField(title="实际支付金额", align=2, sort=7)
	public Double getXfje() {
		return xfje;
	}

	public void setXfje(Double xfje) {
		this.xfje = xfje;
	}
	
	@ExcelField(title="领取方式", align=2, sort=8)
	public String getLqfs() {
		return lqfs;
	}

	public void setLqfs(String lqfs) {
		this.lqfs = lqfs;
	}
	
	@ExcelField(title="领取/邮寄时间", align=2, sort=9)
	public String getLqyjsj() {
		return lqyjsj;
	}

	public void setLqyjsj(String lqyjsj) {
		this.lqyjsj = lqyjsj;
	}
	
	@ExcelField(title="联系方式", align=2, sort=10)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="联系地址", align=2, sort=11)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="订单状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="物流单号", align=2, sort=13)
	public String getWldh() {
		return wldh;
	}

	public void setWldh(String wldh) {
		this.wldh = wldh;
	}
	
	@ExcelField(title="物流单号", align=2, sort=14)
	public Double getYunfei() {
		return yunfei;
	}

	public void setYunfei(Double yunfei) {
		this.yunfei = yunfei;
	}
	
	public List<GoodsOrderDetail> getGoodsOrderDetailList() {
		return goodsOrderDetailList;
	}

	public void setGoodsOrderDetailList(List<GoodsOrderDetail> goodsOrderDetailList) {
		this.goodsOrderDetailList = goodsOrderDetailList;
	}

	public String getKdgs() {
		return kdgs;
	}

	public void setKdgs(String kdgs) {
		this.kdgs = kdgs;
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