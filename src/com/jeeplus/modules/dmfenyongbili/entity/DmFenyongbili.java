/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmfenyongbili.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 分佣比例Entity
 * @author admin
 * @version 2017-05-09
 */
public class DmFenyongbili extends DataEntity<DmFenyongbili> {
	
	private static final long serialVersionUID = 1L;
	private Double wk1;		// 1级微客分佣比例(%)
	private Double wk2;		// 2级微客分佣比例(%)
	private Double jmf;		// 加盟费（元）
	private Double jm1;		// 1级加盟商分佣比例(%)
	private Double jm2;		// 2级加盟商分佣比例(%)
	private Double jmf11;	//第二个加盟费
	private Double jm11;	//第二个1级加盟商分佣比例(%)
	private Double jm22;	//第二个2级加盟商分佣比例(%)
	private Double jmtced;		// 加盟提成额度(元)
	private Double jmtcbl;		// 加盟提成比例(%)
	private Double txdzbl;
	
	

	
	public DmFenyongbili() {
		super();
	}

	public DmFenyongbili(String id){
		super(id);
	}

	@NotNull(message="1级微客分佣比例(%)不能为空")
	@ExcelField(title="1级微客分佣比例(%)", align=2, sort=1)
	public Double getWk1() {
		return wk1;
	}

	public void setWk1(Double wk1) {
		this.wk1 = wk1;
	}
	
	@NotNull(message="2级微客分佣比例(%)不能为空")
	@ExcelField(title="2级微客分佣比例(%)", align=2, sort=2)
	public Double getWk2() {
		return wk2;
	}

	public void setWk2(Double wk2) {
		this.wk2 = wk2;
	}
	
	@NotNull(message="加盟费（元）不能为空")
	@ExcelField(title="加盟费（元）", align=2, sort=3)
	public Double getJmf() {
		return jmf;
	}

	public void setJmf(Double jmf) {
		this.jmf = jmf;
	}
	
	@NotNull(message="1级加盟商分佣比例(%)不能为空")
	@ExcelField(title="1级加盟商分佣比例(%)", align=2, sort=4)
	public Double getJm1() {
		return jm1;
	}

	public void setJm1(Double jm1) {
		this.jm1 = jm1;
	}
	
	@NotNull(message="第二个 加盟费（元）不能为空")
	@ExcelField(title="2级加盟商分佣比例(%)", align=2, sort=5)
	public Double getJm2() {
		return jm2;
	}

	public void setJm2(Double jm2) {
		this.jm2 = jm2;
	}
	
	@NotNull(message="第二个 1级加盟商分佣比例(%)不能为空")
	@ExcelField(title="2级加盟商分佣比例(%)", align=2, sort=6)
	public Double getjmf11() {
		return jmf11;
	}

	public void setjmf11(Double jmf11) {
		this.jmf11 = jmf11;
	}

	@NotNull(message="第二个2级加盟商分佣比例(%)不能为空")
	@ExcelField(title="2级加盟商分佣比例(%)", align=2, sort=7)
	public Double getjm11() {
		return jm11;
	}

	public void setjm11(Double jm11) {
		this.jm11 = jm11;
	}

	@NotNull(message="2级加盟商分佣比例(%)不能为空")
	@ExcelField(title="2级加盟商分佣比例(%)", align=2, sort=8)
	public Double getJm22() {
		return jm22;
	}

	public void setjm22(Double jm22) {
		this.jm22 = jm22;
	}

	
	@NotNull(message="加盟提成额度(元)不能为空")
	@ExcelField(title="加盟提成额度(元)", align=2, sort=9)
	public Double getJmtced() {
		return jmtced;
	}

	public void setJmtced(Double jmtced) {
		this.jmtced = jmtced;
	}
	
	@NotNull(message="加盟提成比例(%)不能为空")
	@ExcelField(title="加盟提成比例(%)", align=2, sort=10)
	public Double getJmtcbl() {
		return jmtcbl;
	}

	public void setJmtcbl(Double jmtcbl) {
		this.jmtcbl = jmtcbl;
	}
	
	@NotNull(message="提现到账比例(%)不能为空")
	@ExcelField(title="提现到账比例(%)", align=2, sort=11)
	public Double getTxdzbl() {
		return txdzbl;
	}

	public void setTxdzbl(Double txdzbl) {
		this.txdzbl = txdzbl;
	}
	
}