/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.yjtx.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.caiwuliushui.dao.CaiwuLiushuiDao;
import com.jeeplus.modules.caiwuliushui.entity.CaiwuLiushui;
import com.jeeplus.modules.dmfenyongbili.entity.DmFenyongbili;
import com.jeeplus.modules.dmfenyongbili.service.DmFenyongbiliService;
import com.jeeplus.modules.member.dao.MemberDao;
import com.jeeplus.modules.member.entity.Member;
import com.jeeplus.modules.member.service.MemberService;
import com.jeeplus.modules.yjtx.entity.Yjtx;
import com.jeeplus.modules.yjtx.dao.YjtxDao;

/**
 * 佣金提现申请Service
 * @author zhaoliangdong
 * @version 2017-06-10
 */
@Service
@Transactional(readOnly = true)
public class YjtxService extends CrudService<YjtxDao, Yjtx> {

	@Autowired
	private CaiwuLiushuiDao caiwuLiushuiDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	public DmFenyongbiliService dmFenyongbiliService;
	@Autowired
	public MemberService memberService;
	
	public Yjtx get(String id) {
		return super.get(id);
	}
	
	public List<Yjtx> findList(Yjtx yjtx) {
		return super.findList(yjtx);
	}
	
	public Page<Yjtx> findPage(Page<Yjtx> page, Yjtx yjtx) {
		return super.findPage(page, yjtx);
	}
	
	@Transactional(readOnly = false)
	public void save(Yjtx yjtx) {
		super.save(yjtx);
	}
	
	@Transactional(readOnly = false)
	public void delete(Yjtx yjtx) {
		super.delete(yjtx);
	}
	
	/**
	 * 审核通过
	 * @param yjtx
	 */
	@Transactional(readOnly = false)
	public void shtg(Yjtx yjtx) {
		
		
		//2.记录财务流水
		CaiwuLiushui ls = new CaiwuLiushui();
		ls.preInsert();
		ls.setJylx("系统后台-提现打款");
		ls.setFukuanfang("管理员");
		ls.setShoukuanfang(yjtx.getWxopenid());
		ls.setJyje(yjtx.getJine());
		
		// 3、分配提现金额，提现金额*系统参数设置里的提现到账比例=daozhangjine（到账金额 ），剩下的累加到用户余额
		DmFenyongbili dmFenyongbili = new DmFenyongbili();
		List<DmFenyongbili> lists = dmFenyongbiliService.findList(dmFenyongbili);
		dmFenyongbili = lists.get(0);
		
		BigDecimal bigYjbl = new BigDecimal(dmFenyongbili.getTxdzbl()); //佣金比例
		BigDecimal bigJine = new BigDecimal(yjtx.getJine()); //提现金额
		BigDecimal bigdzje = new BigDecimal(0);
		BigDecimal bigye = new BigDecimal(0);
		bigdzje = bigJine.multiply(bigYjbl).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP); //转到银行卡里的金额
		bigye = bigJine.subtract(bigdzje); 
		
		
		// 提现到账金额
		yjtx.setDaozhangjine(bigdzje.doubleValue());
		
		// 累加到用户的金额
		Member member = new Member();
		member.setWxopenid(yjtx.getWxopenid());
		if(member != null){
			member.setYue(bigye.doubleValue());
			memberService.jineSave(member);
		}
		
		//1.更新申请记录状态
		dao.updateShtg(yjtx);
		
		caiwuLiushuiDao.insert(ls);
	}

	@Transactional(readOnly = false)
	public void shbtg(Yjtx yjtx) {
		//1.更新申请记录状态
		dao.updateShbtg(yjtx);
		
		//2.将提现金额加回都提线人佣金
		Member m = new Member();
		m.setWxopenid(yjtx.getWxopenid());
		m.setYongjin(yjtx.getJine());
		
		memberDao.updateYongjin(m);
	}
	
	
}