/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.memberchongzhi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.memberchongzhi.entity.MemberChongzhi;
import com.jeeplus.modules.memberchongzhi.dao.MemberChongzhiDao;

/**
 * 充值模块Service
 * @author mxc
 * @version 2017-05-15
 */
@Service
@Transactional(readOnly = true)
public class MemberChongzhiService extends CrudService<MemberChongzhiDao, MemberChongzhi> {

	public MemberChongzhi get(String id) {
		return super.get(id);
	}
	
	public List<MemberChongzhi> findList(MemberChongzhi memberChongzhi) {
		return super.findList(memberChongzhi);
	}
	
	public Page<MemberChongzhi> findPage(Page<MemberChongzhi> page, MemberChongzhi memberChongzhi) {
		return super.findPage(page, memberChongzhi);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberChongzhi memberChongzhi) {
		super.save(memberChongzhi);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberChongzhi memberChongzhi) {
		super.delete(memberChongzhi);
	}
	
	
	
	
}