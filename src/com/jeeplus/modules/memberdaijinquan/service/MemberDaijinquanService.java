/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.memberdaijinquan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.member.dao.MemberDao;
import com.jeeplus.modules.member.entity.Member;
import com.jeeplus.modules.memberdaijinquan.dao.MemberDaijinquanDao;
import com.jeeplus.modules.memberdaijinquan.entity.MemberDaijinquan;

/**
 * 代金券使用Service
 * @author mxc
 * @version 2017-05-15
 */
@Service
@Transactional(readOnly = true)
public class MemberDaijinquanService extends CrudService<MemberDaijinquanDao, MemberDaijinquan> {
	@Autowired
	private MemberDao memberDao;
	
	
	public MemberDaijinquan get(String id) {
		return super.get(id);
	}
	
	public List<MemberDaijinquan> findList(MemberDaijinquan memberDaijinquan) {
		return super.findList(memberDaijinquan);
	}
	
	public Page<MemberDaijinquan> findPage(Page<MemberDaijinquan> page, MemberDaijinquan memberDaijinquan) {
		return super.findPage(page, memberDaijinquan);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberDaijinquan memberDaijinquan) {
		super.save(memberDaijinquan);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberDaijinquan memberDaijinquan) {
		super.delete(memberDaijinquan);
	}

	@Transactional(readOnly = false)
	public void sendAll(MemberDaijinquan memberDaijinquan) {
		List<Member> lists = memberDao.findAllList(new Member());
		for(Member member : lists){
			MemberDaijinquan md = new MemberDaijinquan();
			md.preInsert();
			md.setUpdateDate(null);
			md.setWxopenid(member.getWxopenid());
			md.setName(member.getName());
			md.setMobile(member.getMobile());
			md.setLaiyuan("scff");
			md.setSyzt("0");
			md.setJine(memberDaijinquan.getJine());
			
			dao.insert(md);
		}
		
	}
	
	
	
	
}