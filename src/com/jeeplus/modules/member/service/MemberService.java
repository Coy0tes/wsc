/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.caiwuliushui.dao.CaiwuLiushuiDao;
import com.jeeplus.modules.caiwuliushui.entity.CaiwuLiushui;
import com.jeeplus.modules.member.dao.MemberDao;
import com.jeeplus.modules.member.entity.Member;

/**
 * 会员Service
 * @author mxc
 * @version 2017-05-15
 */
@Service
@Transactional(readOnly = true)
public class MemberService extends CrudService<MemberDao, Member> {
	
	@Autowired
	private CaiwuLiushuiDao caiwuLiushuiDao;

	public Member get(String id) {
		return super.get(id);
	}
	
	public List<Member> findList(Member member) {
		return super.findList(member);
	}
	
	public Page<Member> findPage(Page<Member> page, Member member) {
		return super.findPage(page, member);
	}
	
	@Transactional(readOnly = false)
	public void save(Member member) {
		super.save(member);
	}
	
	@Transactional(readOnly = false)
	public List<Member> update(Member member) {
		super.save(member);
		return null;
//		return super.save(member);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(Member member) {
		super.delete(member);
	}

	@Transactional(readOnly = false)
	public void updateYue(Member member) {
		//1.给会员增加余额
		dao.updateYue(member);
		
		//2.记录充值流水
		CaiwuLiushui e = new CaiwuLiushui();
		e.preInsert();
		e.setJylx("系统后台充值");
		e.setFukuanfang("管理员");
		e.setShoukuanfang("平台");
		e.setJyje(member.getChongzhijine());
		
		caiwuLiushuiDao.insert(e);
	}

	@Transactional(readOnly = false)
	public void jineSave(Member m) {
		dao.jineSave(m);
	}

}